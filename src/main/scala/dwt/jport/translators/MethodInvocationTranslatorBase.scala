package dwt.jport.translators

import dwt.jport.ITypeBindigImplicits._
import dwt.jport.ast.expressions.MethodInvocationInterface

trait MethodInvocationTranslatorBase extends ExpressionTranslator {
  def translate(node: MethodInvocationInterface) =
    node.name.translate +
      typeArguments(node) +
      s"(${arguments(node)})"

  private def arguments(node: MethodInvocationInterface) =
    node.arguments.map(_.translate).mkString(", ")

  private def typeArguments(node: MethodInvocationInterface) = {
    val args = node.typeArguments.map(_.translate).mkString(", ")
    if (args.isEmpty) "" else s"!(${args})"
  }
}
