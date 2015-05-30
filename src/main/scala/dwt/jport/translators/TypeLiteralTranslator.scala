package dwt.jport.translators

import dwt.jport.ITypeBindigImplicits._
import dwt.jport.ast.expressions.TypeLiteral

object TypeLiteralTranslator extends ExpressionTranslator {
  def translate(node: TypeLiteral) =
    node.typeBinding.canonicalType.translate + ".classinfo"
}
