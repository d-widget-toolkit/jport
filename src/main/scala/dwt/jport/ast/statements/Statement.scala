package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }
import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.analyzers.VisitData
import dwt.jport.analyzers.JPortConverter
import dwt.jport.ast.AstNode

abstract class TypedStatement[T <: ASTNode](node: T) extends AstNode(node) {
  def isMultiline = true

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
}
