package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.ASTNode

trait Visitor {
  protected def accept[T <: ASTNode](nodes: Array[T])(visitor: (T, VisitData[T]) => Unit): Unit = {

    if (nodes.isEmpty) return

    var prev: Option[T] = None
    var next: Option[T] = None //if (nodes.length >= 2) Option(nodes(1)) else None

    for ((node, index) <- nodes.view.zipWithIndex) {
      val isFirst = index == 0
      next = if (index != nodes.length - 1) Some(nodes(index + 1)) else None

      visitor(node, new VisitData(isFirst, next, prev))
      prev = Some(node)
    }
  }
}
