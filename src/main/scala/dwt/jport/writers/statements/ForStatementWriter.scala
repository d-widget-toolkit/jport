package dwt.jport.writers.statements

import dwt.jport.ast.statements.ForStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object ForStatementWriter extends WriterObject[ForStatement, ForStatementWriter]

class ForStatementWriter extends Writer[ForStatement] {
  override def write(importWriter: ImportWriter, node: ForStatement): Unit = {
    super.write(importWriter, node)
    writeHeader

    importWriter += node.imports
  }

  private def writeHeader = {
    buffer.append("for (");
    buffer.join(node.initializers.map(_.translate))
    buffer += ';'
    buffer += node.expression.map(" " + _.translate).getOrElse("")
    buffer += ';'

    if (node.updaters.nonEmpty)
      buffer += ' '

    buffer.join(node.updaters.map(_.translate))
    buffer += ')'
  }
}
