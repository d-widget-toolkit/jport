package dwt.jport.writers

import dwt.jport.ast.MethodDeclaration

object MethodDeclarationWriter extends WriterObject[MethodDeclaration, MethodDeclarationWriter]

class MethodDeclarationWriter extends BodyDeclarationWriter[MethodDeclaration] {
  def write(importWriter: ImportWriter, node: MethodDeclaration): Unit = {
    this.node = node
    this.importWriter = importWriter

    //writeModifiers
    writeReturnType
    buffer :+ ' '
    writeName
    buffer :+ '('
    writeParameters
    buffer :+ ')'
    writeBody
    //importWriter :+ node.imports
  }

  def postWrite(): Unit = {
    if (node.hasNonEmptyBody)
      buffer.append('}', nl, nl)
    else
      buffer :+ nl;
  }

  private def writeReturnType = buffer.append(node.returnType)
  private def writeName = buffer.append(node.name)
  private def writeParameters = buffer.join(node.parameters)

  private def writeBody = {
    if (!node.hasBody)
      buffer :+ ';'

    else if (node.hasEmptyBody)
      buffer :+ " {}"

    else
      buffer.append('{', nl)
  }
}
