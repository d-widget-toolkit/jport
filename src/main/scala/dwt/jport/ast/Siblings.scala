package dwt.jport.ast

import dwt.jport.analyzers.VisitData

trait Siblings {
  type JdtNodeType

  protected def visitData: VisitData[JdtNodeType]

  val hasNext = visitData.next.isDefined
  val hasPrev = visitData.prev.isDefined

  val next = visitData.next
  val prev = visitData.prev
}
