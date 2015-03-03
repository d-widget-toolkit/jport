package dwt.jport.writers

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.BodyDeclaration
import dwt.jport.JPorter
import dwt.jport.ast.FieldDeclaration
import dwt.jport.translators.Constant
import dwt.jport.writers.statements.VariableDeclarationWriter

object FieldDeclarationWriter extends WriterObject[FieldDeclaration, FieldDeclarationWriter]

class FieldDeclarationWriter extends BodyDeclarationWriter[FieldDeclaration] with VariableDeclarationWriter[FieldDeclaration] {
  override def write(importWriter: ImportWriter, node: FieldDeclaration): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeModifiers
    writeType
    writeNamesAndConstantValues
    buffer += ';'
  }
}
