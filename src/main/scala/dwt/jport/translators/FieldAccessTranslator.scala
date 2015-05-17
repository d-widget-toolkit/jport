package dwt.jport.translators

import dwt.jport.ast.expressions.FieldAccess
import dwt.jport.Symbol

object FieldAccessTranslator extends ExpressionTranslator {
  def translate(node: FieldAccess) =
    node.expression.translate + "." + node.name.translate
}
