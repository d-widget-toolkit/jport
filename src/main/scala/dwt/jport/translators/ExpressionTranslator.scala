package dwt.jport.translators

import dwt.jport.ast.expressions.Expression

trait ExpressionTranslator {
  def translate(node: Expression): String = throw new Exception("Abstract method")
}
