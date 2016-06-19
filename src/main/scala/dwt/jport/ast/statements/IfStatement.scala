package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ IfStatement => JdtIfStatement }

import dwt.jport.core.JPortAny._
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
    Option(node.getElseStatement).
      map(JPortConverter.convert(_, visitData).asInstanceOf[Block])

  lazy val imports = expression.imports

  lazy val thenStatement = {
    val next = if (elseStatement.isDefined)
      elseStatement.asInstanceOf[Option[AstNode[ASTNode]]]
    else
      visitData.next

    val thenVisit = new VisitData(visitData.isFirst, next, visitData.prev)
    JPortConverter.convert(node.getThenStatement, thenVisit).asInstanceOf[Block]
  }

  override lazy val hasSingleStatementBody =
    thenStatement.statements.length == 1

  lazy val hasSingleElseStatementBody =
    elseStatement.filter(_.statements.length == 1).isDefined

  override def canonicalize() = {
    canonicalizeBody(node.getThenStatement, node.setThenStatement(_))
    canonicalizeBody(node.getElseStatement, node.setElseStatement(_))
    this
  }
}
