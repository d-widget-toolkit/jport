package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ ExpressionStatement => JDtExpressionStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class ExpressionStatement(node: JDtExpressionStatement, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings {

  type NodeType = AstNode[ASTNode]

  val expression = node.getExpression.toJPort
  val imports = expression.imports

  override def isMultiline = false
}
