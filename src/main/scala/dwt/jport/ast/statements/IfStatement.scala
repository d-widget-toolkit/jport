package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ IfStatement => JdtIfStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class IfStatement(node: JdtIfStatement, private[jport] override val visitData: VisitData[AstNode[ASTNode]])
  extends Statement(node)
  with Siblings {

  type NodeType = AstNode[ASTNode]

  val expression = node.getExpression.toJPort
  val thenStatement = node.getThenStatement
  val elseStatement = Option(node.getElseStatement)
  val imports = expression.imports
}
