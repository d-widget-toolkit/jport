package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ SynchronizedStatement => JdtSynchronizedStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class SynchronizedStatement(node: JdtSynchronizedStatement, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings {

  val body = node.getBody
  val expression = node.getExpression.toJPort
  val imports = expression.imports
}
