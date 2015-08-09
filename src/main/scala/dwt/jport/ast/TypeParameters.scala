package dwt.jport.ast

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ITypeBinding

import dwt.jport.Type

trait TypeParameters {
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
