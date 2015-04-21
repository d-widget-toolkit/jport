package dwt.jport.writers.statements

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.ast.statements.ExpressionStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object ExpressionStatementWriter extends WriterObject[ExpressionStatement, ExpressionStatementWriter]

class ExpressionStatementWriter extends Writer[ExpressionStatement] {
  def write(importWriter: ImportWriter, node: ExpressionStatement): Unit = {
    this.node = node
    this.importWriter = importWriter

    buffer.append(node.expression, ';')
  }

  def postWrite(): Unit = {
    buffer :+ nl

    if (node.next.isDefined) {
      if (!isExpressionStatement(node.next) && !isAdjacentLine(node.next.get))
        buffer :+ nl
    }
  }
}
