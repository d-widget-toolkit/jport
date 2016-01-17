package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ ContinueStatement => JdtContinueStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class ContinueStatement(node: JdtContinueStatement, private[jport] override val visitData: VisitData[Statement])
  extends ControlFlowStatement(node, visitData) {

  override val label = Option(node.getLabel).map(_.toJPort)
}
