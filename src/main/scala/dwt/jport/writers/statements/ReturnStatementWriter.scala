package dwt.jport.writers.statements

import dwt.jport.ast.statements.ReturnStatement
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object ReturnStatementWriter extends WriterObject[ReturnStatement, ReturnStatementWriter]

class ReturnStatementWriter extends Writer[ReturnStatement]
  with NewlineWriter[ReturnStatement] {
  override def write() = {
    buffer += "return"
    buffer += node.expression.map(' ' + _.translate).getOrElse("")
    buffer += ';'

    importWriter += node.imports
    this
  }

  override def postWrite: Unit = super[NewlineWriter].postWrite()
}
