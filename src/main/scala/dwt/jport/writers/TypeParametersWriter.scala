package dwt.jport.writers

import dwt.jport.DCoder
import dwt.jport.ast.TypeParameters

trait TypeParametersWriter[T <: TypeParameters] extends Node[T] with Buffer {
  protected def writeTypeParameters: Unit = {
    if (node.typeParameters.isEmpty) return

    val params = node.typeParameters.map { e =>
      if (e._2.length == 1) s"${e._1} : ${e._2.head}" else e._1
    }

    buffer.append(' ', '(').join(params).append(')')
  }

  protected def writeTemplateConstraints: Unit = {
    val constraints = node.multipleBoundTypeParameters.
      flatMap(p => p._2.map(e => s"is(${p._1} : ${e})"))

    if (constraints.isEmpty) return

    buffer.append(" if (").join(constraints, " && ").append(')')
  }
}
