package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ BreakStatement => JdtBreakStatement }
import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class BreakStatement(node: JdtBreakStatement, private[dwt] override val visitData: VisitData)
  extends ControlFlowStatement(node, visitData) {

  override val label = Option(node.getLabel).map(_.toJPort)
}
