package dwt.jport.translators

import dwt.jport.ITypeBindigImplicits._

import dwt.jport.ast.expressions.MethodInvocation

object MethodInvocationTranslator extends MethodInvocationTranslatorBase {
  def translate(node: MethodInvocation) =
    expression(node) + super.translate(node)

  private def expression(node: MethodInvocation) =
    node.expression.map(_.translate + ".").getOrElse("")
}
