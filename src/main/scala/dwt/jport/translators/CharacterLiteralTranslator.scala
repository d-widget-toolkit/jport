package dwt.jport.translators

import dwt.jport.ast.expressions.CharacterLiteral

object CharacterLiteralTranslator extends ExpressionTranslator {
  def translate(node: CharacterLiteral) = node.escapedValue
}
