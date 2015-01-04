package dwt.jport.ast

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ MethodDeclaration => JdtMethodDeclaration }
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.Modifier

import dwt.jport.Symbol
import dwt.jport.Type
import dwt.jport.analyzers.Modifiers

class MethodDeclaration(node: JdtMethodDeclaration) extends BodyDeclaration(node) {
  private val binding = node.resolveBinding

  val isVirtual = !isFinal && !isPrivate && !isStatic

  override val translatedModifiers = Modifiers.convert(modifiers, isVirtual)

  val unescapedName = node.getName.getIdentifier
  val name = Symbol.translate(unescapedName)

  val returnType = Type.translate(binding.getReturnType)
  val parameters = binding.getParameterTypes.map(Type.translate(_))

  val body = node.getBody
  val hasBody = body != null
  val hasEmptyBody = hasBody && body.statements.isEmpty
  val hasNonEmptyBody = hasBody && body.statements.nonEmpty
}
