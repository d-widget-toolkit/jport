package dwt.jport.writers

import dwt.jport.ast.TypeDeclaration

object TypeDeclarationWriter extends WriterObject[TypeDeclaration, TypeDeclarationWriter]

class TypeDeclarationWriter extends BodyDeclarationWriter[TypeDeclaration] {
  def write(importWriter: ImportWriter, node: TypeDeclaration): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeModifiers
    writeDeclaration
    writeTypeParameters
    writeBases
    writeTemplateConstraints
    writeBody

    importWriter :+ node.imports
    buffer.increaseIndentation
  }

  def postWrite(): Unit = {
    buffer.decreaseIndentation
    buffer :+ '}'
    buffer.append(nl, nl)
  }

  private def writeDeclaration = buffer.append(typeName, ' ', node.name)

  private def writeTypeParameters: Unit = {
    if (node.typeParameters.isEmpty) return

    val params = node.typeParameters.map { e =>
      if (e._2.length == 1) s"${e._1} : ${e._2.head}" else e._1
    }

    buffer.append(' ', '(')
    buffer.join(params)
    buffer.append(')')
  }

  private def writeBases = {
    val bases = if (node.superclass == null) node.interfaces
    else node.superclass +: node.interfaces

    if (bases.nonEmpty) {
      buffer :+ " : "
      buffer.join(bases)
    }
  }

  private def writeTemplateConstraints: Unit = {
    val constraints = node.multipleBoundTypeParameters.
      flatMap(p => p._2.map(e => s"is(${p._1} : ${e})"))

    if (constraints.isEmpty) return

    buffer :+ " if ("
    buffer.join(constraints, " && ")
    buffer :+ ')'
  }

  private def typeName = if (node.isInterface) "interface" else "class"

  private def writeBody = {
    if (node.hasMembers) buffer :+ nl else buffer :+ ' '
    buffer :+ '{'
    if (node.hasMembers) buffer :+ nl
  }
}
