package dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }

import dwt.jport.ast.expressions.InfixExpression

object InfixExpressionTranslator extends ExpressionTranslator {
  private val IsOperators = Map(
    JdtInfixExpression.Operator.EQUALS -> "is",
    JdtInfixExpression.Operator.NOT_EQUALS -> "!is")

  def translate(node: InfixExpression) =
    node.leftOperand.translate + " " +
      translateOperator(node) + " " +
      node.rightOperand.translate

  private def isAnyOperandsNullLiteral(node: InfixExpression) =
    node.leftOperand.nodeType == ASTNode.NULL_LITERAL ||
      node.rightOperand.nodeType == ASTNode.NULL_LITERAL

  private def translateOperator(node: InfixExpression) =
    if (isAnyOperandsNullLiteral(node))
      IsOperators.getOrElse(node.operator, node.operator.toString)
    else
      node.operator.toString
}
