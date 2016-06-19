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

    importWriter += node.imports
    this
  }

  def writeElse = if (node.elseStatement.isDefined) buffer.append("else")

  override def postWrite =
    if (node.hasSingleStatementBody && node.thenStatement.hasNext)
      buffer += nl
}
