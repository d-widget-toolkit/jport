package dwt.jport.translators

import dwt.jport.ast.expressions.StringLiteral

object StringLiteralTranslator {
  def translate(node: StringLiteral) = node.escapedValue
}
