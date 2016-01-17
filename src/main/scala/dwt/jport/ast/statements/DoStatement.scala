package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ DoStatement => JdtDoStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class DoStatement(node: JdtDoStatement, private[jport] override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement

  val body = node.getBody
  val expression = node.getExpression.toJPort
  val imports = expression.imports
}
