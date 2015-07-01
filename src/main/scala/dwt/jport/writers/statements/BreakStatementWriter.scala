package dwt.jport.writers.statements

import dwt.jport.writers.WriterObject
import dwt.jport.writers.Writer
import dwt.jport.writers.ImportWriter
import dwt.jport.ast.statements.BreakStatement

object BreakStatementWriter extends WriterObject[BreakStatement, BreakStatementWriter]

class BreakStatementWriter extends Writer[BreakStatement] {
  override def write(importWriter: ImportWriter, node: BreakStatement): Unit = {
    super.write(importWriter, node)

    buffer += "break"
    buffer += node.label.map(' ' + _.translate).getOrElse("")
    buffer += ';'
  }

  override def postWrite(): Unit = {
    buffer += nl
  }
}
