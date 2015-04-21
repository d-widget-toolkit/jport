package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ ExpressionStatement => JDtExpressionStatement }
import org.eclipse.jdt.core.dom.Statement

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class ExpressionStatement(node: JDtExpressionStatement, protected override val visitData: VisitData[Statement])
  extends AstNode(node)
  with Siblings {

  type JdtNodeType = Statement

  def expression = node.getExpression.toJPort.translate
}
