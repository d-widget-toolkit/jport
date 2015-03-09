package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ NumberLiteral => JdtNumberLiteral }
import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }
import dwt.jport.ast.AstNode
import dwt.jport.analyzers.VisitData
import dwt.jport.JPorter
import dwt.jport.translators.ExpressionTranslator

object Expression {
  def toJPort(node: JdtExpression): Expression = {
    node match {
      case n: JdtNumberLiteral => new NumberLiteral(n)
      case n: JdtInfixExpression => new InfixExpression(n)
      case _ =>
        JPorter.diagnostic.unhandled(s"Unhandled type ${node.getClass.getName} in ${getClass.getName}")
        null
    }
  }
}

abstract class Expression(node: JdtExpression) extends AstNode(node) {
  def translate: String
}
