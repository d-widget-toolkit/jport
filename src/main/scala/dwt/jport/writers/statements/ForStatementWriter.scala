package dwt.jport.writers.statements

import dwt.jport.ast.statements.ForStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object ForStatementWriter extends WriterObject[ForStatement, ForStatementWriter]

class ForStatementWriter extends Writer[ForStatement] {
  def write(importWriter: ImportWriter, node: ForStatement): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeHeader
    writeBody

    importWriter += node.imports
  }

  def postWrite(): Unit = {
    /*if (node.hasBody)
      buffer += '}'

    buffer += nl

    if (node.next.isDefined)
      buffer += nl*/
  }

  private def writeHeader = {
    buffer.append("for (");
    buffer.join(node.initializers.map(_.translate))
    buffer += ';'
    buffer += node.expression.map(" " + _.translate).getOrElse("")
    buffer += ';'
    buffer.join(node.updaters.map(_.translate))
    buffer += ')'
  }

  private def writeBody = {
    /*if (node.hasBody && !node.hasEmptyBody) buffer += nl
    else buffer += ' ';

    buffer += '{'*/
  }
}
