package dwt.jport.writers

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.BodyDeclaration

import dwt.jport.JPorter
import dwt.jport.ast.FieldDeclaration

object FieldDeclarationWriter extends WriterObject[FieldDeclaration, FieldDeclarationWriter]

class FieldDeclarationWriter extends BodyDeclarationWriter[FieldDeclaration] {
  def write(importWriter: ImportWriter, node: FieldDeclaration): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeModifiers
    writeType
    writeNames
  }

  def postWrite(): Unit = {
    buffer :+ nl

    if (node.next.isDefined) {
      if (!isField(node.next) || !isAdjacentLine(node.next.get))
        buffer :+ nl
    }
  }

  private def writeType = buffer.append(node.typ, ' ')
  private def writeNames = buffer.join(node.names).append(';')

  private def isField(node: Option[BodyDeclaration]) =
    node.isDefined && node.get.getNodeType == ASTNode.FIELD_DECLARATION

  private def isAdjacentLine(node: BodyDeclaration) =
    lineNumber(node) == this.node.lineNumber + 1

  private def lineNumber(node: BodyDeclaration) =
    JPorter.compilationUnit.get.getLineNumber(node)
}
