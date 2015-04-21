package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ Assignment => JdtAssignment }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.AssignmentTranslator

class Assignment(node: JdtAssignment) extends Expression(node) {
  val leftHandSide = node.getLeftHandSide.toJPort.translate
  val rightHandSide = node.getRightHandSide.toJPort.translate
  val operator = node.getOperator.toString

  override def translate = AssignmentTranslator.translate(this)
}
