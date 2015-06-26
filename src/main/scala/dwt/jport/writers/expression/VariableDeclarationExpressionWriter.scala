package dwt.jport.writers.expression

import dwt.jport.ast.expressions.VariableDeclarationExpression
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.statements.VariableDeclarationWriter

object VariableDeclarationExpressionWriter
  extends WriterObject[VariableDeclarationExpression, VariableDeclarationExpressionWriter]

class VariableDeclarationExpressionWriter
    extends Writer[VariableDeclarationExpression]
    with VariableDeclarationWriter[VariableDeclarationExpression] {

  override def postWrite(): Unit = {}
}
