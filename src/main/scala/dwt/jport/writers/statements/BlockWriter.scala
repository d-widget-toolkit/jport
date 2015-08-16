package dwt.jport.writers.statements

import dwt.jport.ast.statements.Block
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.NewlineWriter

object BlockWriter extends WriterObject[Block, BlockWriter]

class BlockWriter extends Writer[Block] with NewlineWriter[Block] {
  override def write(importWriter: ImportWriter, node: Block): Unit = {
    super.write(importWriter, node)

    if (node.isEmpty)
      buffer += " {"

    else
      buffer.append(nl, '{', nl)

    buffer.increaseIndentation
  }

  override def postWrite(): Unit = {
    buffer.decreaseIndentation.append('}')
    super[NewlineWriter].postWrite()
  }
}
