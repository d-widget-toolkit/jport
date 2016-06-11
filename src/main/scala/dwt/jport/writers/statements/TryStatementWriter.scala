package dwt.jport.writers.statements

import dwt.jport.ast.statements.TryStatement
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object TryStatementWriter extends WriterObject[TryStatement, TryStatementWriter]

class TryStatementWriter extends Writer[TryStatement] {
  override def write() = {
    buffer += "try"
    this
  }

  def writeFinally = if (node.`finally`.isDefined) buffer.append(nl, "finally")
}
