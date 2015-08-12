package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ LabeledStatement => JdtLabeledStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class LabeledStatement(node: JdtLabeledStatement, protected override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type JdtNodeType = Statement

  val body = node.getBody
  val label = node.getLabel.toJPort
}
