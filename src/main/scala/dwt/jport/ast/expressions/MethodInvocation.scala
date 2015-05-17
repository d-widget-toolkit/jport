package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ MethodInvocation => JdtMethodInvocation }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.MethodInvocationTranslator

class MethodInvocation(node: JdtMethodInvocation) extends Expression(node) {
  val name = node.getName.toJPort

  override def translate = MethodInvocationTranslator.translate(this)
}
