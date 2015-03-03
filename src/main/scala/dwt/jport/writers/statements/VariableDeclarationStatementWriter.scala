package dwt.jport.writers.statements

import dwt.jport.ast.statements.VariableDeclarationStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import org.eclipse.jdt.core.dom.ASTNode
import dwt.jport.JPorter
import dwt.jport.ast.expressions.Expression

object VariableDeclarationStatementWriter extends WriterObject[VariableDeclarationStatement, VariableDeclarationStatementWriter]

class VariableDeclarationStatementWriter extends Writer[VariableDeclarationStatement] /*with VariableDeclarationWriter[VariableDeclarationStatement]*/ {
  def write(importWriter: ImportWriter, node: VariableDeclarationStatement): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeType
    writeNamesAndInitializers
    buffer += ';'
  }

  def postWrite(): Unit = {
    buffer :+ nl

    if (node.next.isDefined) {
      if (!isField(node.next) || !isAdjacentLine(node.next.get))
        buffer :+ nl
    }
  }

  private def writeType = buffer.append(node.typ, ' ')
  private def writeNamesAndInitializers =
    buffer.join(namesInitializers)

  private def isField(node: Option[VariableDeclarationStatement#JdtNodeType]) =
    node.isDefined && node.get.getNodeType == ASTNode.FIELD_DECLARATION

  private def isAdjacentLine(node: VariableDeclarationStatement#JdtNodeType) =
    lineNumber(node) == this.node.lineNumber + 1

  private def lineNumber(node: VariableDeclarationStatement#JdtNodeType) =
    JPorter.compilationUnit.getLineNumber(node)

  private def namesInitializers: scala.collection.mutable.Buffer[String] =
    node.names.zip(node.initializers).map {
      case (name, value) =>
        val initializer = if (value != null) value.translate else ""
        //val constValue = Expression.toJPort(value, null) //Constant.translate(value)
        if (initializer.isEmpty) name else s"${name} = ${initializer}"
    }
}
