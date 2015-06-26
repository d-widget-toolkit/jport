package dwt.jport.translators

import dwt.jport.ast.expressions.VariableDeclarationExpression
import dwt.jport.writers.expression.VariableDeclarationExpressionWriter
import dwt.jport.DCoder

object VariableDeclarationExpressionTranslator extends ExpressionTranslator {
  def translate(node: VariableDeclarationExpression): String = {
    VariableDeclarationExpressionWriter.write(null, node)
    ""
  }
}
