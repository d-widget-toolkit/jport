package dwt.jport.writers.statements

import dwt.jport.writers.WriterObject
import dwt.jport.writers.Writer
import dwt.jport.ast.statements.EmptyStatement
import dwt.jport.writers.ImportWriter

object EmptyStatementWriter extends WriterObject[EmptyStatement, EmptyStatementWriter]

class EmptyStatementWriter extends Writer[EmptyStatement] {
  def write(importWriter: ImportWriter, node: EmptyStatement): Unit = {
    this.node = node
    this.importWriter = importWriter

    buffer.append(" {}", nl)
  }

  def postWrite(): Unit = {

  }
}
