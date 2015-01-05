package dwt.jport.ast

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.analyzers.VisitData

trait Siblings[T <: ASTNode] {
  protected val visitData: VisitData[T]

  val hasNext = visitData.next.isDefined
  val hasPrev = visitData.prev.isDefined

  val next = visitData.next
  val prev = visitData.prev
}
