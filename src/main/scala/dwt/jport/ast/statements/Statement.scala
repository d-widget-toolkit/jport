package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }
import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.ast.AstNode

abstract class TypedStatement[T <: ASTNode](node: T) extends AstNode(node) {
  def isMultiline = true
}
