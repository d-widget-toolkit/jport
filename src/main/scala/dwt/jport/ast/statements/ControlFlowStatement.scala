package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.ast.expressions.Expression

abstract class ControlFlowStatement(node: JdtStatement, protected override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement

  val label: Option[Expression]

  val keyword =
    getClass.getName.split('.').last.toLowerCase.replace("statement", "")
}
