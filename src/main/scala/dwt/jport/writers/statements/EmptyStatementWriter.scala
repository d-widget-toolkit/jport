package dwt.jport.writers.statements

import dwt.jport.writers.WriterObject
import dwt.jport.writers.Writer
import dwt.jport.ast.statements.EmptyStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object EmptyStatementWriter extends WriterObject[EmptyStatement, EmptyStatementWriter]

class EmptyStatementWriter extends Writer[EmptyStatement]
  with NewlineWriter[EmptyStatement] {

  override def write() = {
    buffer.append(" {}")
    this
  }

  override def postWrite = super[NewlineWriter].postWrite()
}
