package dwt.jport.writers.statements

import dwt.jport.ast.statements.VariableDeclarationStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object VariableDeclarationStatementWriter extends WriterObject[VariableDeclarationStatement, VariableDeclarationStatementWriter]

class VariableDeclarationStatementWriter
  extends Writer[VariableDeclarationStatement]
  with VariableDeclarationWriter[VariableDeclarationStatement] {

  override def write(importWriter: ImportWriter, node: VariableDeclarationStatement): Unit = {
    super.write(importWriter, node)
    buffer += ';'

    importWriter += node.imports
  }
}
