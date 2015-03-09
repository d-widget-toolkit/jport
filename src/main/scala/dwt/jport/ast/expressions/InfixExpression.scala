package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }

class InfixExpression(node: JdtInfixExpression) extends Expression(node) {
  override def translate = Expression.toJPort(node.getLeftOperand).translate +
    " " + node.getOperator + " " +
    Expression.toJPort(node.getRightOperand).translate
}
