package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ SwitchStatement => JdtSwitchStatement }
import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class SwitchStatement(node: JdtSwitchStatement, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings {

  val expression = node.getExpression.toJPort
  val statements = node.statements.asInstanceOf[JavaList[JdtStatement]]
  val imports = expression.imports
}
