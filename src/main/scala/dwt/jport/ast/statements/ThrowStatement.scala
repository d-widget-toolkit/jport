package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ ThrowStatement => JdtThrowStatement }
import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class ThrowStatement(node: JdtThrowStatement, private[jport] override val visitData: VisitData[AstNode[ASTNode]])
  extends Statement(node)
  with Siblings {

  type NodeType = AstNode[ASTNode]

  val expression = node.getExpression.toJPort
  val imports = expression.imports
}
