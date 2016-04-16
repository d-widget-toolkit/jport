package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ ReturnStatement => JdtReturnStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class ReturnStatement(node: JdtReturnStatement, private[jport] override val visitData: VisitData[AstNode[ASTNode]])
  extends Statement(node)
  with Siblings {

  type NodeType = AstNode[ASTNode]

  val expression = Option(node.getExpression).map(_.toJPort)
  val imports = expression.map(_.imports).getOrElse(Seq())
}
