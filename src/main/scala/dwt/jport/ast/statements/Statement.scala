package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }
import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.analyzers.VisitData
import dwt.jport.analyzers.JPortConverter
import dwt.jport.ast.AstNode

abstract class TypedStatement[T <: ASTNode](node: T) extends AstNode(node) {
  def isMultiline = true

  override def canonicalize = this

  protected def extractBody(statement: JdtStatement, visitData: VisitData) = {
    val body = JPortConverter.convert(statement, visitData)

    if (body.nodeType == ASTNode.BLOCK) {
      val block = body.asInstanceOf[Block]
      val statements = block.statements.to[Array]
      if (statements.length == 1) statements(0) else body
    }

    else
      body
  }

  /**
   * Canonicalizes the given node body by wrapping it in a block.
   *
   * @param node = the node to canonicalize
   * @param block = the given block is called if node was canonicalized.
   *  The block has to remove the parent of the given node, directly or
   *  indirectly
   */
  protected def canonicalizeBody(node: JdtStatement, block: JdtStatement => Unit): Unit = {
    if (node == null) return
    val nodeType = node.getNodeType

    if (nodeType != ASTNode.BLOCK) {
      val newBody = node.getAST.newBlock
      block(newBody)

      if (nodeType != ASTNode.EMPTY_STATEMENT) {
        val statements = newBody.statements.asInstanceOf[JavaList[JdtStatement]]
        statements.add(node)
      }
    }
  }
}
