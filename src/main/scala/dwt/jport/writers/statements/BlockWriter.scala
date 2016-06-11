package dwt.jport.writers.statements

import dwt.jport.ast.statements.Block
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.NewlineWriter

object BlockWriter extends WriterObject[Block, BlockWriter]

class BlockWriter extends Writer[Block] with NewlineWriter[Block] {
  override def write() = {
    if (node.hasSingleStatementBody)
      buffer += nl

    else if (node.isEmpty)
      buffer += " {"

    else
      buffer.append(nl, '{', nl)

    buffer.increaseIndentation
    this
  }

  override def postWrite(): Unit = {
    buffer.decreaseIndentation

    if (!node.hasSingleStatementBody)
      buffer.append('}')

    super[NewlineWriter].postWrite()
  }
}
