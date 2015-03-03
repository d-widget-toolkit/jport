package dwt.jport.translators

import dwt.jport.ast.expressions.NumberLiteral

object NumberLiteralTranslator extends ExpressionTranslator {
  def translate(node: NumberLiteral): String = node.token
}
