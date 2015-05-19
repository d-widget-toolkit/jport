package dwt.jport.translators

import dwt.jport.ast.expressions.PrefixExpression

object PrefixExpressionTranslator extends ExpressionTranslator {
  def translate(node: PrefixExpression) =
    node.operator.toString() + node.operand.translate
}
