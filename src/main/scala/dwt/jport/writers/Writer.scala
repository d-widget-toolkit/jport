package dwt.jport.writers

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.core.JPortAny._

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
  var importWriter: ImportWriter = null

  def write(): Writer[_]
  def postWrite: Unit = {}

  protected def isAdjacentLine(node: AstNode[_]) =
    lineNumber(node) == this.node.lineNumber + 1

  protected def lineNumber(node: AstNode[_]) =
    JPorter.compilationUnit.getLineNumber(node)

  protected def isExpressionStatement(node: Option[AstNode[_]]) =
    node.isDefined && node.get.nodeType == ASTNode.EXPRESSION_STATEMENT
}

abstract class WriterObject[Node <: AstNode[_], Subclass <: Writer[Node]](implicit manifest: Manifest[Subclass]) {
  private def newInstance = manifest.runtimeClass.newInstance.asInstanceOf[Subclass]

  def apply(importWriter: ImportWriter, node: Node) = newInstance.tap { e =>
    e.importWriter = importWriter
    e.node = node
  }
}
