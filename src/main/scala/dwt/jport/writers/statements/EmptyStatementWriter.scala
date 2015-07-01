package dwt.jport.writers.statements

import dwt.jport.writers.WriterObject
import dwt.jport.writers.Writer
import dwt.jport.ast.statements.EmptyStatement
import dwt.jport.writers.ImportWriter

object EmptyStatementWriter extends WriterObject[EmptyStatement, EmptyStatementWriter]

class EmptyStatementWriter extends Writer[EmptyStatement] {
  override def write(importWriter: ImportWriter, node: EmptyStatement): Unit = {
    super.write(importWriter, node)

    buffer.append(" {}", nl)
  }
}
