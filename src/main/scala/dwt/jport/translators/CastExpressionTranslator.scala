package dwt.jport.translators

import dwt.jport.ast.expressions.CastExpression

object CastExpressionTranslator extends ExpressionTranslator {
  def translate(node: CastExpression) =
    s"cast(${node.typ}) ${node.expression.translate}"
}
