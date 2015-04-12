package dwt.jport.ast

import java.util.ArrayList

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.{ MethodDeclaration => JdtMethodDeclaration }
import org.eclipse.jdt.core.dom.SingleVariableDeclaration
import org.eclipse.jdt.core.dom.Statement

import dwt.jport.Symbol
import dwt.jport.Type
import dwt.jport.analyzers.Modifiers
import dwt.jport.analyzers.VisitData
import dwt.jport.translators.ImportTranslator

class MethodDeclaration(node: JdtMethodDeclaration, protected override val visitData: VisitData[JdtBodyDeclaration])
  extends BodyDeclaration(node)
  with TypeParameters
  with Siblings {

  type JdtNodeType = JdtBodyDeclaration

  import Type.fullyQualfiedName

  private lazy val binding = node.resolveBinding

  val imports = {
    val paramTypes = binding.getParameterTypes.
      map(e => if (e.isArray) e.getElementType else e)

    val boundTypes = binding.getTypeParameters.flatMap(_.getTypeBounds)
    val returnType = binding.getReturnType
    val types = boundTypes ++ paramTypes :+ returnType

    ImportTranslator.translate(types, declaringClass)
  }

  val isVirtual = !isFinal && !isPrivate && !isStatic

  override val translatedModifiers = Modifiers.convert(modifiers, isVirtual)

  val unescapedName = node.getName.getIdentifier
  val name = Symbol.translate(unescapedName)

  val returnType = Type.translate(binding.getReturnType)

  private val typedParameters = node.parameters.asInstanceOf[JavaList[SingleVariableDeclaration]]
  val parameters = typedParameters.map(buildParameter(_))

  val body = Option(node.getBody)

  val statements =
    body.map(e => e.statements.asInstanceOf[JavaList[Statement]]).
      getOrElse(new ArrayList[Statement])

  val hasBody = body.isDefined
  val hasEmptyBody = hasBody && statements.isEmpty
  val hasNonEmptyBody = hasBody && statements.nonEmpty

  protected override def typeParametersBinding = binding.getTypeParameters

  private def buildParameter(param: SingleVariableDeclaration) = {
    val typ = Type.translate(param.getType.resolveBinding)
    val name = Symbol.translate(param.getName.getIdentifier)

    if (param.isVarargs) s"$typ[] $name ..." else s"$typ $name"
  }
}
