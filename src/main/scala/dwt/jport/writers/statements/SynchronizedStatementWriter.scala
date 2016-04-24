package dwt.jport.writers.statements

import dwt.jport.ast.statements.SynchronizedStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object SynchronizedStatementWriter extends WriterObject[SynchronizedStatement, SynchronizedStatementWriter]

class SynchronizedStatementWriter extends Writer[SynchronizedStatement] {
  override def write(importWriter: ImportWriter, node: SynchronizedStatement): Unit = {
    super.write(importWriter, node)
    writeHeader

    if (node.hasSingleStatementBody) {
      buffer += nl
      buffer.increaseIndentation
    }

    importWriter += node.imports
  }

  override def postWrite =
    if (node.hasSingleStatementBody)
      buffer.decreaseIndentation

  private def writeHeader = {
    buffer.append("synchronized (", node.expression.translate, ')');
  }
}
