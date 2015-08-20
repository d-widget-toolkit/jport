package dwt.jport.writers.statements

import dwt.jport.ast.statements.DoStatement
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object DoStatementWriter extends WriterObject[DoStatement, DoStatementWriter]

class DoStatementWriter extends Writer[DoStatement]
  with NewlineWriter[DoStatement] {

  override def write(importWriter: ImportWriter, node: DoStatement): Unit = {
    super.write(importWriter, node)

    buffer += "do"
    importWriter += node.imports
  }

  override def postWrite = {
    buffer.append(" while(", node.expression.translate, ");")
    super[NewlineWriter].postWrite()
  }
}
