package dwt.jport.translators

import dwt.jport.ast.expressions.Assignment

object AssignmentTranslator extends ExpressionTranslator {
  def translate(node: Assignment) =
    node.leftHandSide.translate + " " +
      node.operator + " " +
      node.rightHandSide.translate
}
