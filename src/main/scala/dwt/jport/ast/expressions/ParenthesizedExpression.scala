package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ ParenthesizedExpression => JdtParenthesizedExpression }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.ParenthesizedExpressionTranslator

class ParenthesizedExpression(node: JdtParenthesizedExpression)
  extends Expression(node) {

  val expression = node.getExpression.toJPort

  override def translate = ParenthesizedExpressionTranslator.translate(this)
  override lazy val importTypeBindings = expression.importTypeBindings
}
