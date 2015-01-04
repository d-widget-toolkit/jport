package dwt.jport.writers

import dwt.jport.ast.MethodDeclaration
import dwt.jport.analyzers.Modifiers

object MethodDeclarationWriter extends WriterObject[MethodDeclaration, MethodDeclarationWriter]

class MethodDeclarationWriter extends BodyDeclarationWriter[MethodDeclaration] with TypeParametersWriter[MethodDeclaration] {
  def write(importWriter: ImportWriter, node: MethodDeclaration): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeModifiers
    writeReturnType
    writeName
    writeTypeParameters
    writeParameters
    writeBody
    //importWriter :+ node.imports
  }

  def postWrite(): Unit = {
    if (node.hasNonEmptyBody)
      buffer.append('}', nl, nl)
    else
      buffer :+ nl;
  }

  private def writeReturnType = buffer.append(node.returnType, ' ')
  private def writeName = buffer :+ node.name

  private def writeParameters =
    buffer.append('(').join(node.parameters).append(')')

  private def writeBody = {
    if (!node.hasBody)
      buffer :+ ';'

    else if (node.hasEmptyBody)
      buffer :+ " {}"

    else
      buffer.append('{', nl)
  }
}
