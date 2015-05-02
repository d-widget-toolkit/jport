package dwt.jport.translators

import dwt.jport.ast.expressions.ConditionalExpression

object ConditionalExpressionTranslator extends ExpressionTranslator {
  def translate(node: ConditionalExpression) =
    node.expression.translate + " ? " +
      node.thenExpression.translate + " : " +
      node.elseExpression.translate
}
