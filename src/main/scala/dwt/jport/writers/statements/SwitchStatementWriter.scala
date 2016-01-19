package dwt.jport.writers.statements

import dwt.jport.ast.statements.SwitchStatement
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object SwitchStatementWriter extends WriterObject[SwitchStatement, SwitchStatementWriter]

class SwitchStatementWriter extends Writer[SwitchStatement]
  with NewlineWriter[SwitchStatement] {

  override def write(importWriter: ImportWriter, node: SwitchStatement): Unit = {
    super.write(importWriter, node)

    buffer.append("switch (", node.expression.translate, ')', nl, '{', nl)
    buffer.increaseIndentation(2)

    importWriter += node.imports
  }

  override def postWrite(): Unit = {
    buffer.decreaseIndentation(2)
    buffer.append('}')
    super.postWrite()
  }
}
