package dwt.jport.ast

import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.{ MethodDeclaration => JdtMethodDeclaration }
import dwt.jport.Symbol
import dwt.jport.Type

class MethodDeclaration(node: JdtMethodDeclaration) extends BodyDeclaration(node) {
  private val binding = node.resolveBinding

  val unescapedName = node.getName.getIdentifier
  val name = Symbol.translate(unescapedName)

  val returnType = Type.translate(binding.getReturnType)
  val parameters = binding.getParameterTypes.map(Type.translate(_))

  val body = node.getBody
  val hasBody = body != null
  val hasEmptyBody = hasBody && body.statements.isEmpty
  val hasNonEmptyBody = hasBody && body.statements.nonEmpty
}
