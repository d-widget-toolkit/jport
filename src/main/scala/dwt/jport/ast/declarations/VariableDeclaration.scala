package dwt.jport.ast.declarations

import scala.collection.JavaConversions._
import scala.collection.mutable.Buffer

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.ITypeBinding
import org.eclipse.jdt.core.dom.{ Type => JdtType }
import org.eclipse.jdt.core.dom.VariableDeclarationFragment

import dwt.jport.ITypeBindigImplicits._
import dwt.jport.Symbol
import dwt.jport.Type
import dwt.jport.analyzers.Modifiers
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.ImportTranslator

trait VariableDeclaration extends Siblings {
  private type JavaList[T] = java.util.List[T]

  protected def fragments: JavaList[VariableDeclarationFragment]
  protected def modifiers: JavaList[IExtendedModifier]
  protected def rawType: JdtType
  protected def fragmentBindings = fragments.map(_.resolveBinding)
  protected def node: ASTNode
  protected def declaringClass: ITypeBinding

  def initializers =
    fragments.map(e => Option(e.getInitializer).map(_.toJPort))

  private val typeBinding = rawType.resolveBinding
  val typ = Type.translate(typeBinding)

  val imports = {
    val types = initializers.
      flatMap(_.map(_.importTypeBindings).getOrElse(Buffer())) :+
      typeBinding.canonicalType

    ImportTranslator.translate(types, declaringClass)
  }

  def names = fragmentBindings.map(e => Symbol.translate(e.getName))

  def translatedModifiers =
    Modifiers.convert(modifiers, variable = true,
      primitiveType = rawType.isPrimitiveType())

  def constantValues = fragmentBindings.map(_.getConstantValue)

}
