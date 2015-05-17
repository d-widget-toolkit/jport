package dwt.jport.translators

import dwt.jport.ITypeBindigImplicits._

import dwt.jport.ast.expressions.MethodInvocation

object MethodInvocationTranslator extends ExpressionTranslator {
  def translate(node: MethodInvocation) =
    node.expression.translate +
      "." + node.name.translate +
      typeArguments(node) +
      s"(${arguments(node)})"

  private def arguments(node: MethodInvocation) =
    node.arguments.map(_.translate).mkString(", ")

  private def typeArguments(node: MethodInvocation) = {
    val args = node.typeArguments.map(_.translate).mkString(", ")
    if (args.isEmpty) "" else s"!(${args})"
  }
}
