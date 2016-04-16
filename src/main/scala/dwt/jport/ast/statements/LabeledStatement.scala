package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ LabeledStatement => JdtLabeledStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class LabeledStatement(node: JdtLabeledStatement, private[jport] override val visitData: VisitData[AstNode[ASTNode]])
  extends Statement(node)
  with Siblings {

  type NodeType = AstNode[ASTNode]

  val body = node.getBody
  val label = node.getLabel.toJPort
}
