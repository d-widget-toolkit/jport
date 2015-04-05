package dwt.jport.translators

import dwt.jport.ast.expressions.ArrayAccess

object ArrayAccessTranslator extends ExpressionTranslator {
  def translate(node: ArrayAccess) =
    node.array.translate + s"[${node.index.translate}]"
}
