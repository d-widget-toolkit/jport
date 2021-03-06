package dwt.jport.writers.statements

import dwt.jport.ast.statements.ControlFlowStatement
import dwt.jport.writers.WriterObject
import dwt.jport.writers.Writer
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object ControlFlowStatementWriter extends WriterObject[ControlFlowStatement, ControlFlowStatementWriter]

class ControlFlowStatementWriter extends Writer[ControlFlowStatement]
  with NewlineWriter[ControlFlowStatement] {

  override def write() = {
    buffer += node.keyword
    buffer += node.label.map(' ' + _.translate).getOrElse("")
    buffer += ';'

    this
  }

  override def postWrite = super[NewlineWriter].postWrite()
}
