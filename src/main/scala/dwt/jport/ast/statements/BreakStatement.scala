package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ BreakStatement => JdtBreakStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class BreakStatement(node: JdtBreakStatement, protected override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type JdtNodeType = Statement

  val label = Option(node.getLabel).map(_.toJPort)
}
