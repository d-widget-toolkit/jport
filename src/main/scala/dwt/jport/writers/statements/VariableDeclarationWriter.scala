package dwt.jport.writers.statements

import org.eclipse.jdt.core.dom.ASTNode
import dwt.jport.JPorter
import dwt.jport.ast.AstNode
import dwt.jport.ast.declarations.VariableDeclaration
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.NewlineWriter

trait VariableDeclarationWriter[AstNodeType <: AstNode[_] with VariableDeclaration]
  extends Writer[AstNodeType]
  with NewlineWriter[AstNodeType] {

  override def write(importWriter: ImportWriter, node: AstNodeType): Unit = {
    super.write(importWriter, node)

    writeType
    writeNamesAndInitializers
  }

  override def postWrite(): Unit = super[NewlineWriter].postWrite()
  protected def writeType = buffer.append(node.typ, ' ')

  protected def writeNamesAndInitializers =
    buffer.join(namesInitializers)

  private def isField(node: Option[AstNodeType#NodeType]) =
    node.filter(toAstNode(_).nodeType == ASTNode.FIELD_DECLARATION).isDefined

  private def isAdjacentLine(node: AstNodeType#NodeType) =
    lineNumber(node) == this.node.lineNumber + 1

  protected def isVariableDeclarationStatement(node: Option[AstNode[_]]) =
    node.filter(_.nodeType == ASTNode.VARIABLE_DECLARATION_STATEMENT).isDefined

  private def lineNumber(node: AstNodeType#NodeType) =
    JPorter.compilationUnit.getLineNumber(toAstNode(node))

  private def namesInitializers: scala.collection.mutable.Buffer[String] =
    node.names.zip(node.initializers).map {
      case (name, value) =>
        val initializer = value.map(_.translate).getOrElse("")
        if (initializer.isEmpty) name else s"${name} = ${initializer}"
    }

  def toAstNode[T](node: T) = node.asInstanceOf[AstNode[ASTNode]]
}
