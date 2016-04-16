package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ CatchClause => JdtCatchClause }
import org.eclipse.jdt.core.dom.{ TryStatement => JdtTryStatement }

import dwt.jport.analyzers.JPortConverter
import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class TryStatement(node: JdtTryStatement, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings {

  type NodeType = AstNode[ASTNode]

  private def typedCatchClauses =
    node.catchClauses.asInstanceOf[JavaList[JdtCatchClause]].to[Array]

  val catchClauses =
    JPortConverter.convert[JdtCatchClause, CatchClause](typedCatchClauses)

  def body = JPortConverter.convert(node.getBody, visitData)

  def `finally` =
    Option(node.getFinally).map(JPortConverter.convert(_, visitData))
}
