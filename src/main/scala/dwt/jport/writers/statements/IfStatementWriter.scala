package dwt.jport.writers.statements

import dwt.jport.ast.statements.IfStatement
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter

object IfStatementWriter extends WriterObject[IfStatement, IfStatementWriter] {
  def writeElse = writer.writeElse
}

class IfStatementWriter extends Writer[IfStatement] {
  override def write(importWriter: ImportWriter, node: IfStatement): Unit = {
    super.write(importWriter, node)

    buffer.append("if (", node.expression.translate, ")")
    importWriter += node.imports
  }

  def writeElse = if (node.elseStatement.isDefined) buffer.append("else")
}
