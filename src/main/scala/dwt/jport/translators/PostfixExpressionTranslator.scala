package dwt.jport.translators

import dwt.jport.ast.expressions.PostfixExpression

object PostfixExpressionTranslator extends ExpressionTranslator {
  def translate(node: PostfixExpression) =
    node.operand.translate + node.operator.toString()
}
