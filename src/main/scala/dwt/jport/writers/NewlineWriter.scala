package dwt.jport.writers

import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.MethodDeclaration
import org.eclipse.jdt.core.dom.ASTNode
import dwt.jport.ast.statements.Block

trait NewlineWriter[T <: Siblings with AstNode[_]] extends Node[T] with Buffer {
  def postWrite(): Unit = {
    if (parentIsDoStatement) return

    buffer += nl

    if (!hasNext) return

    if (node.isMultiline || next.isMultiline)
      buffer += nl

    else if (bothIsSingleline && !parentHasSingleStatementBody) {
      if (!nextIsAdjecent)
        buffer += nl
    }
  }

  protected def hasNext: Boolean = node.next.
    filterNot(_.nodeType == ASTNode.BREAK_STATEMENT).isDefined

  private def next: AstNode[_] = {
    assert(node.hasNext, "Should only be called when node has a next node")
    node.next.get
  }

  private def bothIsSingleline = !node.isMultiline && !next.isMultiline
  private def nextIsAdjecent = node.isAdjacentLine(next)
  private def nextIsDifferentType = node.nodeType != next.nodeType

  private def parentIsDoStatement =
    node.parent.map(_.nodeType == ASTNode.DO_STATEMENT).getOrElse(false)

  private def parentHasSingleStatementBody =
    node.parent.filter(_.hasSingleStatementBody).isDefined
}
