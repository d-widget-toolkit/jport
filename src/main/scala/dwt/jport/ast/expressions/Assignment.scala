package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ Assignment => JdtAssignment }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.AssignmentTranslator

class Assignment(node: JdtAssignment) extends Expression(node) {
  val leftHandSide = node.getLeftHandSide.toJPort
  val rightHandSide = node.getRightHandSide.toJPort
  val operator = node.getOperator.toString

  override lazy val importTypeBindings =
    leftHandSide.importTypeBindings ++ rightHandSide.importTypeBindings

  override def translate = AssignmentTranslator.translate(this)
}
