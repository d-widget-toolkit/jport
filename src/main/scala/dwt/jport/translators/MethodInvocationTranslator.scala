package dwt.jport.translators

import dwt.jport.ast.expressions.MethodInvocation

object MethodInvocationTranslator extends ExpressionTranslator {
  def translate(node: MethodInvocation) = node.name.translate + "()";
}
