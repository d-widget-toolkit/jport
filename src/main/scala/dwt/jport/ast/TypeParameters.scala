package dwt.jport.ast

import scala.collection.JavaConversions._
import scala.collection.mutable.Buffer

import org.eclipse.jdt.core.dom.ITypeBinding
import org.eclipse.jdt.core.dom.QualifiedName
import org.eclipse.jdt.core.dom.SimpleName
import org.eclipse.jdt.core.dom.SimpleType
import org.eclipse.jdt.core.dom.{ Type => JdtType }
import org.eclipse.jdt.core.dom.TypeParameter

import dwt.jport.Symbol
import dwt.jport.Type

trait TypeParameters {
  private type JavaList[T] = java.util.List[T]

  protected def typeParametersBinding: Array[ITypeBinding]

  val typeParameters = typeParametersBinding.map { p =>
    val bounds = p.getTypeBounds.map(Type.translate(_))
    val typeName = Type.translate(p)
    (typeName, bounds)
  }

  val unboundTypeParameters = typeParameters.filter(_._2.isEmpty)
  val singleBoundTypeParameters = typeParameters.filter(_._2.length == 1)
  val multipleBoundTypeParameters = typeParameters.filter(_._2.length >= 2)
}
