package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }

import dwt.jport.ast.expressions.ExpressionImplicits._

class InfixExpression(node: JdtInfixExpression) extends Expression(node) {
  val leftOperand = node.getLeftOperand.toJPort
  val rightOperand = node.getRightOperand.toJPort

  override lazy val importTypeBindings =
    leftOperand.importTypeBindings ++ rightOperand.importTypeBindings

  override def translate =
    leftOperand.translate + " " +
      node.getOperator + " " +
      rightOperand.translate
}
