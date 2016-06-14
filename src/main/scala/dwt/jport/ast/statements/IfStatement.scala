package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ IfStatement => JdtIfStatement }

import dwt.jport.analyzers.JPortConverter
import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class IfStatement(node: JdtIfStatement, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings {

  lazy val expression = node.getExpression.toJPort

  lazy val elseStatement =
    Option(node.getElseStatement).map(JPortConverter.convert(_, visitData))

  lazy val imports = expression.imports

  lazy val thenStatement = {
    val next = if (elseStatement.isDefined)
      elseStatement.asInstanceOf[Option[AstNode[ASTNode]]]
    else
      visitData.next

    val thenVisit = new VisitData(visitData.isFirst, next, visitData.prev)
    extractBody(node.getThenStatement, thenVisit)
  }

  override lazy val hasSingleStatementBody = {
    val nodeType = thenStatement.nodeType
    nodeType != ASTNode.BLOCK && nodeType != ASTNode.EMPTY_STATEMENT
  }

  lazy val hasSingleElseStatementBody =
    elseStatement.map(_.nodeType).
      filter(e => e != ASTNode.BLOCK && e != ASTNode.EMPTY_STATEMENT).isDefined
}
