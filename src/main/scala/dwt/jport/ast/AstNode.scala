package dwt.jport.ast

import org.eclipse.jdt.core.dom.ASTNode
import dwt.jport.JPorter

abstract class AstNode[T <: ASTNode](protected val node: T) {
  protected type JavaList[T] = java.util.List[T]

  private def unit = JPorter.compilationUnit

  def lineNumber = unit.getLineNumber(node)
}
