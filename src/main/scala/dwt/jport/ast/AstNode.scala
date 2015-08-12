package dwt.jport.ast

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }

import dwt.jport.JPorter

abstract class AstNode[T <: ASTNode](val node: T) {
  protected type JavaList[T] = java.util.List[T]

  val nodeType = node.getNodeType
  val startPosition = node.getStartPosition

  private def unit = JPorter.compilationUnit

  def lineNumber = unit.getLineNumber(this)

  protected def declaringClass = {
    var parent = node.getParent

    while (parent != null && parent.getNodeType != ASTNode.TYPE_DECLARATION)
      parent = parent.getParent

    parent.asInstanceOf[JdtTypeDeclaration].resolveBinding()
  }

  def isMultiline = true
}
