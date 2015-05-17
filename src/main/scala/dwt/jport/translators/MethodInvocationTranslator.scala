package dwt.jport.translators

import dwt.jport.ast.expressions.MethodInvocation

object MethodInvocationTranslator extends ExpressionTranslator {
  def translate(node: MethodInvocation) =
    node.name.translate + s"(${arguments(node)})";

  private def arguments(node: MethodInvocation) =
    node.arguments.map(_.translate).mkString(", ")
}
