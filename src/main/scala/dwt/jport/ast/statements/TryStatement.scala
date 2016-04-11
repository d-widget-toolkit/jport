package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ CatchClause => JdtCatchClause }
import org.eclipse.jdt.core.dom.{ TryStatement => JdtTryStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.analyzers.JPortConverter

class TryStatement(node: JdtTryStatement, private[jport] override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement

  private def typedCatchClauses =
    node.catchClauses.asInstanceOf[JavaList[JdtCatchClause]].to[Array]

  val catchClauses =
    JPortConverter.convert[JdtCatchClause, CatchClause](typedCatchClauses)

  def body = JPortConverter.convert(node.getBody, visitData)

  def `finally` =
    Option(node.getFinally).map(JPortConverter.convert(_, visitData))
}
