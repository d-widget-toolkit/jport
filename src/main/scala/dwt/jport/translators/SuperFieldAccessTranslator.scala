package dwt.jport.translators

import dwt.jport.ast.expressions.SuperFieldAccess

object SuperFieldAccessTranslator extends ExpressionTranslator {
  def translate(node: SuperFieldAccess) = s"super.${node.name.translate}"
}
