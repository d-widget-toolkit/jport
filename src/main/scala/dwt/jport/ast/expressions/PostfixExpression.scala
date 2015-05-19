package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ PostfixExpression => JdtPostfixExpression }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.PostfixExpressionTranslator

class PostfixExpression(node: JdtPostfixExpression) extends Expression(node) {
  val operand = node.getOperand.toJPort
  val operator = node.getOperator

  override def translate = PostfixExpressionTranslator.translate(this)
}
