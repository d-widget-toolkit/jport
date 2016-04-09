package dwt.jport.writers.statements

import dwt.jport.ast.statements.ThrowStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.NewlineWriter

object ThrowStatementWriter extends WriterObject[ThrowStatement, ThrowStatementWriter]

class ThrowStatementWriter extends Writer[ThrowStatement]
  with NewlineWriter[ThrowStatement] {

  override def write(importWriter: ImportWriter, node: ThrowStatement): Unit = {
    super.write(importWriter, node)

    buffer.append("throw ", node.expression.translate)
    importWriter += node.imports
  }

  override def postWrite: Unit = {
    buffer.append(';')
    super[NewlineWriter].postWrite()
  }
}
