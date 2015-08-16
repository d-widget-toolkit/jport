package dwt.jport.writers.statements

import dwt.jport.ast.statements.ExpressionStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.NewlineWriter

object ExpressionStatementWriter extends WriterObject[ExpressionStatement, ExpressionStatementWriter]

class ExpressionStatementWriter extends Writer[ExpressionStatement]
  with NewlineWriter[ExpressionStatement] {

  override def write(importWriter: ImportWriter, node: ExpressionStatement): Unit = {
    super.write(importWriter, node)

    buffer.append(node.expression.translate, ';')

    importWriter += node.imports
  }

  override def postWrite(): Unit = super[NewlineWriter].postWrite()
}
