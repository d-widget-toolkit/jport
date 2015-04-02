package dwt.jport.translators

import dwt.jport.ast.expressions.BooleanLiteral

object BooleanLiteralTranslator extends ExpressionTranslator {
  def translate(node: BooleanLiteral) = node.toString
}
