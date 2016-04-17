package dwt.jport.writers.statements

import dwt.jport.ast.statements.WhileStatement
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object WhileStatementWriter extends WriterObject[WhileStatement, WhileStatementWriter]

class WhileStatementWriter extends Writer[WhileStatement] {
  override def write(importWriter: ImportWriter, node: WhileStatement): Unit = {
    super.write(importWriter, node)

    buffer.append("while (", node.expression.translate, ")")
    importWriter += node.imports
  }
}
