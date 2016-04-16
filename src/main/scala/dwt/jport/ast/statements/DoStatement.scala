package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ DoStatement => JdtDoStatement }

import dwt.jport.ast.AstNode
import dwt.jport.analyzers.VisitData
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class DoStatement(node: JdtDoStatement, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings {

  type NodeType = AstNode[ASTNode]

  val body = node.getBody
  val expression = node.getExpression.toJPort
  val imports = expression.imports
}
