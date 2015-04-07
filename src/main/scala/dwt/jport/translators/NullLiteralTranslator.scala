package dwt.jport.translators

import dwt.jport.ast.expressions.NullLiteral

object NullLiteralTranslator {
  def translate(node: NullLiteral) = "null"
}
