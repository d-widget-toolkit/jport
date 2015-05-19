package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ PrefixExpression => JdtPrefixExpression }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.PrefixExpressionTranslator

class PrefixExpression(node: JdtPrefixExpression) extends Expression(node) {
  val operand = node.getOperand.toJPort
  val operator = node.getOperator

  override def translate = PrefixExpressionTranslator.translate(this)
}
