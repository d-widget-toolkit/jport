package dwt.jport.writers

import dwt.jport.ast.FieldDeclaration
import dwt.jport.writers.statements.VariableDeclarationWriter

object FieldDeclarationWriter extends WriterObject[FieldDeclaration, FieldDeclarationWriter]

class FieldDeclarationWriter extends BodyDeclarationWriter[FieldDeclaration] with VariableDeclarationWriter[FieldDeclaration] {
  override def write(importWriter: ImportWriter, node: FieldDeclaration): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeModifiers
    writeType
    writeNamesAndInitializers
    buffer += ';'
  }
}
