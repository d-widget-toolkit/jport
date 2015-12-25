package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ ReturnStatement => JdtReturnStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class ReturnStatement(node: JdtReturnStatement, protected override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement

  val expression = Option(node.getExpression).map(_.toJPort)
  val imports = expression.map(_.imports).getOrElse(Seq())
}
