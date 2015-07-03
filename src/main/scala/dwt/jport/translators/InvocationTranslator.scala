package dwt.jport.translators

import dwt.jport.ITypeBindigImplicits._
import dwt.jport.ast.Invocation

trait InvocationTranslator extends ExpressionTranslator {
  def translate(node: Invocation) =
    node.name.translate +
      typeArguments(node) +
      s"(${arguments(node)})"

  private def arguments(node: Invocation) =
    node.arguments.map(_.translate).mkString(", ")

  private def typeArguments(node: Invocation) = {
    val args = node.typeArguments.map(_.translate).mkString(", ")
    if (args.isEmpty) "" else s"!(${args})"
  }
}
