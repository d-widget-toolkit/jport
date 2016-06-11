package dwt.jport.writers.statements

import dwt.jport.ast.statements.SwitchCase
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object SwitchCaseWriter extends WriterObject[SwitchCase, SwitchCaseWriter]

class SwitchCaseWriter extends Writer[SwitchCase]
  with NewlineWriter[SwitchCase] {

  override def write() = {
    buffer.unindent {
      if (node.isDefault)
        buffer += "default:"
      else
        buffer.append("case ", node.expression.get.translate, ':')
    }

    importWriter += node.imports
    this
  }

  override protected def hasNext = false

  override def postWrite(): Unit = {
    super.postWrite()
  }
}
