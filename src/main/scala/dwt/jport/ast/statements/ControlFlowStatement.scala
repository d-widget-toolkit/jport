package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }
import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.ast.expressions.Expression

abstract class ControlFlowStatement(node: JdtStatement, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings {

  val label: Option[Expression]

  val keyword =
    getClass.getName.split('.').last.toLowerCase.replace("statement", "")
}
