package dwt.jport.translators

import dwt.jport.ast.expressions.ThisExpression

object ThisExpressionTranslator extends ExpressionTranslator {
  def translate(node: ThisExpression) = "this"
}
