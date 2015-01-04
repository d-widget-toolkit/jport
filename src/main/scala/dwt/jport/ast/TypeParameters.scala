package dwt.jport.ast

import scala.collection.JavaConversions._
import scala.collection.mutable.Buffer

import org.eclipse.jdt.core.dom.{ Type => JdtType }
import org.eclipse.jdt.core.dom.TypeParameter
import org.eclipse.jdt.core.dom.SimpleType
import org.eclipse.jdt.core.dom.SimpleName
import org.eclipse.jdt.core.dom.QualifiedName

import dwt.jport.Symbol

trait TypeParameters {
  private type JavaList[T] = java.util.List[T]

  protected def typedTypeParameters: JavaList[TypeParameter]

  val typeParameters = typedTypeParameters.map { p =>
    val bounds = simpleTypeBounds(p.typeBounds)
    val typeName = Symbol.translate(p.getName.getIdentifier)
    (typeName, namesOfBounds(bounds))
  }

  val unboundTypeParameters = typeParameters.filter(_._2.isEmpty)
  val singleBoundTypeParameters = typeParameters.filter(_._2.length == 1)
  val multipleBoundTypeParameters = typeParameters.filter(_._2.length >= 2)

  private def simpleTypeBounds(bounds: JavaList[_]) =
    bounds.asInstanceOf[JavaList[JdtType]].
      filter(_.isSimpleType).map(_.asInstanceOf[SimpleType])

  private def namesOfBounds(bounds: Buffer[SimpleType]) = bounds.map { e =>
    e.getName match {
      case s: SimpleName => Symbol.translate(s.getIdentifier)
      case q: QualifiedName => Symbol.translate(q.getFullyQualifiedName)
      case t => throw new Exception(s"Unhalded bounds type '$t'")
    }
  }
}
