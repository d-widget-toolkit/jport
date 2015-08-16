package dwt.jport.writers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.ast.BodyDeclaration
import dwt.jport.ast.MethodDeclaration

object MethodDeclarationWriter extends WriterObject[MethodDeclaration, MethodDeclarationWriter]

class MethodDeclarationWriter extends BodyDeclarationWriter[MethodDeclaration]
  with TypeParametersWriter[MethodDeclaration]
  with NewlineWriter[MethodDeclaration] {

  override def write(importWriter: ImportWriter, node: MethodDeclaration): Unit = {
    super.write(importWriter, node)

    writeModifiers
    writeReturnType
    writeName
    writeTypeParameters
    writeParameters
    writeBody

    importWriter :+ node.imports
    buffer.increaseIndentation
  }

  override def postWrite(): Unit = {
    buffer.decreaseIndentation

    if (node.hasBody)
      buffer :+ '}'

    super[NewlineWriter].postWrite()
  }

  private def writeReturnType =
    buffer += node.returnType.map(_ + ' ').getOrElse("")

  private def writeName = buffer :+ node.name

  private def writeParameters =
    buffer.append('(').join(node.parameters).append(')')

  private def writeBody = {
    if (!node.hasBody)
      buffer :+ ';'

    else if (node.hasEmptyBody)
      buffer :+ " {"

    else
      buffer.append(nl, '{', nl)
  }

  private def hasNonEmptyBody(node: Option[BodyDeclaration]) =
    node.filter(_.nodeType == ASTNode.METHOD_DECLARATION).
      map(_.asInstanceOf[MethodDeclaration]).
      filter(e => e.body.isDefined && e.body.get.statements.nonEmpty).nonEmpty

  private def hasNonEmptyBody(node: MethodDeclaration): Boolean =
    hasNonEmptyBody(Some(node))

  private def isMethod(node: Option[BodyDeclaration]) =
    node.isDefined && node.get.nodeType == ASTNode.METHOD_DECLARATION
}
