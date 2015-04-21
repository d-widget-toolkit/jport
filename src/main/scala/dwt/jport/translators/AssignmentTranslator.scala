package dwt.jport.translators

import dwt.jport.ast.expressions.Assignment

object AssignmentTranslator extends ExpressionTranslator {
  def translate(node: Assignment) =
    node.leftHandSide + " " + node.operator + " " + node.rightHandSide
}
