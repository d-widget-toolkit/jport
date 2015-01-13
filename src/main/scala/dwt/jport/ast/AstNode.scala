package dwt.jport.ast

import org.eclipse.jdt.core.dom.ASTNode
import dwt.jport.JPorter

abstract class AstNode[T <: ASTNode](protected val node: T) {
  private def unit = JPorter.compilationUnit.get

  def lineNumber = unit.getLineNumber(node)
}
