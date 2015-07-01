package dwt.jport.writers.statements

import dwt.jport.ast.statements.Block
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object BlockWriter extends WriterObject[Block, BlockWriter]

class BlockWriter extends Writer[Block] {
  override def write(importWriter: ImportWriter, node: Block): Unit = {
    super.write(importWriter, node)

    if (node.isEmpty)
      buffer += " {"

    else
      buffer.append(nl, '{', nl)

    buffer.increaseIndentation
  }

  override def postWrite(): Unit = {
    buffer.decreaseIndentation

    buffer.append('}', nl)

    if (!node.isEmpty && node.next.isDefined)
      buffer += nl
  }
}
