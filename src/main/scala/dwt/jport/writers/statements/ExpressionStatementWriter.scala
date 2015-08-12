package dwt.jport.writers.statements

import dwt.jport.ast.statements.ExpressionStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object ExpressionStatementWriter extends WriterObject[ExpressionStatement, ExpressionStatementWriter]

class ExpressionStatementWriter extends Writer[ExpressionStatement] {
  override def write(importWriter: ImportWriter, node: ExpressionStatement): Unit = {
    super.write(importWriter, node)

    buffer.append(node.expression.translate, ';')

    importWriter += node.imports
  }

  override def postWrite(): Unit = {
    buffer :+ nl

    if (node.next.isDefined) {
      if (!isExpressionStatement(node.next) && !isAdjacentLine(node.next.get))
        buffer :+ nl
    }
  }
}
