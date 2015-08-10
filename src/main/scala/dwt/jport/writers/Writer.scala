package dwt.jport.writers

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.DCoder
import dwt.jport.JPorter
import dwt.jport.ast.AstNode
import dwt.jport.util.ThreadLocalVariable

trait Buffer {
  protected val nl = DCoder.nl
  protected val buffer = DCoder.dcoder
}

trait Node[T] {
  var node: T = _
}

trait Writer[T <: AstNode[_]] extends Node[T] with Buffer {
  protected var importWriter: ImportWriter = null

  def write(importWriter: ImportWriter, node: T): Unit = {
    this.node = node
    this.importWriter = importWriter
  }

  def postWrite: Unit = {}

  protected def isAdjacentLine(node: ASTNode) =
    lineNumber(node) == this.node.lineNumber + 1

  protected def lineNumber(node: ASTNode) =
    JPorter.compilationUnit.getLineNumber(node)

  protected def isExpressionStatement(node: Option[ASTNode]) =
    node.isDefined && node.get.getNodeType == ASTNode.EXPRESSION_STATEMENT
}

abstract class WriterObject[Node <: AstNode[_], Subclass <: Writer[Node]](implicit manifest: Manifest[Subclass]) {
  private def newInstance = manifest.runtimeClass.newInstance.asInstanceOf[Subclass]
  private val _writer = new ThreadLocalVariable(newInstance)
  private def writer = _writer.get

  def write(importWriter: ImportWriter, node: Node) = writer.write(importWriter, node)
  def postWrite: Unit = writer.postWrite
}
