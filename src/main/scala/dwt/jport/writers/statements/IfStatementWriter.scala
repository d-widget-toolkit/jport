package dwt.jport.writers.statements

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.ast.statements.IfStatement
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter

object IfStatementWriter extends WriterObject[IfStatement, IfStatementWriter]

class IfStatementWriter extends Writer[IfStatement] {
  override def write() = {
    buffer.append("if (", node.expression.translate, ")")

    if (node.hasSingleStatementBody) {
      buffer += nl
      buffer.increaseIndentation
    }

    importWriter += node.imports
    this
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
