package dwt.jport.writers.statements

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.ast.statements.IfStatement
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter

object IfStatementWriter extends WriterObject[IfStatement, IfStatementWriter] {
  def writeElse = writer.writeElse
  def postWriteElse = writer.postWriteElse
}

class IfStatementWriter extends Writer[IfStatement] {
  override def write(importWriter: ImportWriter, node: IfStatement): Unit = {
    super.write(importWriter, node)

    buffer.append("if (", node.expression.translate, ")")

    if (node.hasSingleStatementBody) {
      buffer += nl
      buffer.increaseIndentation
    }

    importWriter += node.imports
  }

  override def postWrite =
    if (node.hasSingleStatementBody)
      buffer.decreaseIndentation

  def writeElse =
    if (node.elseStatement.isDefined) {
      buffer.append("else")

      if (node.hasSingleStatementBody) {
        buffer += nl
        buffer.increaseIndentation
      }
    }

  def postWriteElse =
    if (node.elseStatement.isDefined && node.hasSingleStatementBody)
      buffer.decreaseIndentation
}
