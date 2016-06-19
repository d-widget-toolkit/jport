package dwt.jport.writers.statements

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.core.JPortAny._
import dwt.jport.ast.statements.Block
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.NewlineWriter
import dwt.jport.ast.statements.IfStatement

object BlockWriter extends WriterObject[Block, BlockWriter] {
  def apply(importWriter: ImportWriter, node: NodeType, elseStatement: Boolean) = newInstance.tap { e =>
    e.importWriter = importWriter
    e.node = node
    e.elseStatement = elseStatement
  }
}

class BlockWriter extends Writer[Block] with NewlineWriter[Block] {
  var elseStatement: Boolean = false

  override def write() = {
    if (hasSingleStatementBody)
      buffer += nl

    else if (node.isEmpty)
      buffer += " {"

    else
      buffer.append(nl, '{', nl)

    buffer.increaseIndentation
    this
  }

  override def postWrite(): Unit = {
    buffer.decreaseIndentation

    if (!hasSingleStatementBody) {
      buffer.append('}')
      super[NewlineWriter].postWrite()
    }
  }

  private lazy val hasSingleStatementBody =
    if (elseStatement)
      hasSingleElseStatementBody
    else
      node.parent.filter(_.hasSingleStatementBody).isDefined

  private lazy val hasSingleElseStatementBody = {
    assert(node.parent.map(_.nodeType == ASTNode.IF_STATEMENT).getOrElse(true))
    node.parent.map(_.asInstanceOf[IfStatement].hasSingleElseStatementBody).getOrElse(false)
  }
}
