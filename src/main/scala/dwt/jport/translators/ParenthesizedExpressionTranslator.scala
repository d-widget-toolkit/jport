package dwt.jport.translators

import dwt.jport.ast.expressions.ParenthesizedExpression

object ParenthesizedExpressionTranslator extends ExpressionTranslator {
  def translate(node: ParenthesizedExpression) =
    s"(${node.expression.translate})"
}
