package dwt.jport.writers

import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.MethodDeclaration
import org.eclipse.jdt.core.dom.ASTNode

trait NewlineWriter[T <: Siblings with AstNode[_]] extends Node[T] with Buffer {
  def postWrite(): Unit = {
    if (parentIsDoStatement) return

    buffer += nl

    if (!node.hasNext) return

    if (node.isMultiline || next.isMultiline)
      buffer += nl

    else if (bothIsSingleline) {
      if (!nextIsAdjecent)
        buffer += nl
    }
  }

  private def next: AstNode[_] = {
    assert(node.hasNext, "Should only be called when node has a next node")
    node.next.get
  }

  private def bothIsSingleline = !node.isMultiline && !next.isMultiline
  private def nextIsAdjecent = node.isAdjacentLine(next)
  private def nextIsDifferentType = node.nodeType != next.nodeType

  private def parentIsDoStatement =
    node.parent.map(_.nodeType == ASTNode.DO_STATEMENT).getOrElse(false)
}
