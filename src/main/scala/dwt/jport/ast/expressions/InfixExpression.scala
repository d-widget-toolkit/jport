package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.InfixExpressionTranslator
import dwt.jport.Type

class InfixExpression(node: JdtInfixExpression) extends Expression(node) {
  val operator = node.getOperator
  val leftOperand = node.getLeftOperand.toJPort
  val rightOperand = node.getRightOperand.toJPort

  override lazy val importTypeBindings =
    leftOperand.importTypeBindings ++ rightOperand.importTypeBindings

  override def translate = InfixExpressionTranslator.translate(this)
}
