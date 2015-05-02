package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ ConditionalExpression => JdtConditionalExpression }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.ConditionalExpressionTranslator

class ConditionalExpression(node: JdtConditionalExpression) extends Expression(node) {
  val expression = node.getExpression.toJPort
  val thenExpression = node.getThenExpression.toJPort
  val elseExpression = node.getElseExpression.toJPort

  override lazy val importTypeBindings =
    expression.importTypeBindings ++
      thenExpression.importTypeBindings ++
      elseExpression.importTypeBindings

  def translate = ConditionalExpressionTranslator.translate(this)
}
