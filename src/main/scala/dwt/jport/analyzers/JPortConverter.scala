package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ AbstractTypeDeclaration => JdtAbstractTypeDeclaration }
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }

import dwt.jport.JPorter
import dwt.jport.ast.AbstractTypeDeclaration
import dwt.jport.ast.AstNode
import dwt.jport.ast.TypeDeclaration

object JPortConverter {
  def convert[T <: ASTNode](nodes: Iterable[T]): Iterator[AstNode[_]] = {
    window(nodes).zipWithIndex map {
      case ((prev, node, next), index) =>
        convert(node.get, index == 0, next, prev)
    }
  }

  def convert[T <: ASTNode](node: T, isFirst: Boolean = false,
    next: Option[T] = None,
    prev: Option[T] = None): AstNode[_] = {

    def option[U](o: Option[T]) = o.asInstanceOf[Option[U]]

    node match {
      case n: JdtAbstractTypeDeclaration => {
        val p = option[JdtAbstractTypeDeclaration](prev)
        val ne = option[JdtAbstractTypeDeclaration](next)
        convert(n, isFirst, ne, p)
      }

      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null
      }
    }
  }

  private def convert(node: JdtAbstractTypeDeclaration, isFirst: Boolean,
    next: Option[JdtAbstractTypeDeclaration],
    prev: Option[JdtAbstractTypeDeclaration]): AbstractTypeDeclaration = {

    val ne = next.map(convert(_).asInstanceOf[AbstractTypeDeclaration])
    val p = prev.map(convert(_).asInstanceOf[AbstractTypeDeclaration])
    val visitData = new VisitData(isFirst, ne, p)

    node match {
      case n: JdtTypeDeclaration => new TypeDeclaration(n, visitData)
      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null
      }
    }
  }

  private def window[T](list: Iterable[T]) =
    (None +: list.map(Some(_)) ++: Array(None)).
      sliding(3).map(e => (e(0), e(1), e(2)))
}
