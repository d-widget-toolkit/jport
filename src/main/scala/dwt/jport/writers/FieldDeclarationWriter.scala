package dwt.jport.writers

import dwt.jport.ast.FieldDeclaration
import org.eclipse.jdt.core.dom.{ FieldDeclaration => JdtFieldDeclaration }

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

    if (node.next.isDefined && !node.next.get.isInstanceOf[JdtFieldDeclaration])
      buffer :+ nl
  }

  private def writeType = buffer.append(node.typ, ' ')
  private def writeNames = buffer.join(node.names).append(';')
}
