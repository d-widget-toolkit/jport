package dwt.jport.writers.statements

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.JPorter
import dwt.jport.ast.AstNode
import dwt.jport.ast.declarations.VariableDeclaration
import dwt.jport.translators.Constant
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.ModifierWriter
import dwt.jport.writers.Writer

trait VariableDeclarationWriter[AstNodeType <: AstNode[_] with VariableDeclaration] extends Writer[AstNodeType] {
  def write(importWriter: ImportWriter, node: AstNodeType): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeType
    writeNamesAndConstantValues
  }

  def postWrite(): Unit = {
    buffer :+ nl

    if (node.next.isDefined) {
      if (!isField(node.next) || !isAdjacentLine(node.next.get))
        buffer :+ nl
    }
  }

  protected def writeType = buffer.append(node.typ, ' ')
  protected def writeNamesAndConstantValues =
    buffer.join(namesConstantValues)

  private def isField(node: Option[AstNodeType#JdtNodeType]) =
    node.isDefined && node.get.getNodeType == ASTNode.FIELD_DECLARATION

  private def isAdjacentLine(node: AstNodeType#JdtNodeType) =
    lineNumber(node) == this.node.lineNumber + 1

  private def lineNumber(node: AstNodeType#JdtNodeType) =
    JPorter.compilationUnit.getLineNumber(node)

  private def namesConstantValues: scala.collection.mutable.Buffer[String] =
    node.names.zip(node.constantValues).map {
      case (name, value) =>
        val constValue = Constant.translate(value)
        if (constValue.isEmpty) name else s"${name} = ${constValue}"
    }
}
