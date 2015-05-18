package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ InstanceofExpression => JdtInstanceofExpression }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.InstanceofExpressionTranslator

class InstanceofExpression(node: JdtInstanceofExpression)
  extends Expression(node) {

  val leftOperand = node.getLeftOperand.toJPort
  val rightOperand = node.getRightOperand.resolveBinding

  override def translate = InstanceofExpressionTranslator.translate(this)

  override lazy val importTypeBindings =
    leftOperand.importTypeBindings :+ rightOperand
}
