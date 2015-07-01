package dwt.jport.writers.statements

import dwt.jport.ast.statements.LabeledStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object LabeledStatementWriter extends WriterObject[LabeledStatement, LabeledStatementWriter]

class LabeledStatementWriter extends Writer[LabeledStatement] {
  override def write(importWriter: ImportWriter, node: LabeledStatement): Unit = {
    super.write(importWriter, node)

    buffer.unindent {
      buffer.append(node.label.translate, ':', nl)
    }
  }
}
