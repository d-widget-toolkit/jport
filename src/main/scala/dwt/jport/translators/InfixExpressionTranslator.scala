package dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }

import dwt.jport.Type
import dwt.jport.ast.expressions.InfixExpression
import dwt.jport.ast.expressions.Expression

object InfixExpressionTranslator extends ExpressionTranslator {
  private val SpecialOperatorMapping = Map(
    JdtInfixExpression.Operator.EQUALS -> "is",
    JdtInfixExpression.Operator.NOT_EQUALS -> "!is",
    JdtInfixExpression.Operator.PLUS -> "~")

  def translate(node: InfixExpression) =
    node.leftOperand.translate + " " +
      translateOperator(node) + " " +
      node.rightOperand.translate

  private def isAnyOperandsNullLiteral(node: InfixExpression) =
    node.leftOperand.nodeType == ASTNode.NULL_LITERAL ||
      node.rightOperand.nodeType == ASTNode.NULL_LITERAL

  private def translateOperator(node: InfixExpression) =
    if (requiresSpecialOperatorMapping(node))
      SpecialOperatorMapping.getOrElse(node.operator, node.operator.toString)
    else
      node.operator.toString

  private def requiresSpecialOperatorMapping(node: InfixExpression) =
    isAnyOperandsNullLiteral(node) || isAnyOperandsJavaLanString(node)

  private def isAnyOperandsJavaLanString(node: InfixExpression) =
    isJavaLanString(node.leftOperand) || isJavaLanString(node.rightOperand)

  private def isJavaLanString(expression: Expression) =
    Option(expression.typeBinding).filter(Type.isJavaLangType(_, "String")).
      isDefined
}
