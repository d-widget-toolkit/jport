package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ ArrayCreation => JdtArrayCreation }
import org.eclipse.jdt.core.dom.Dimension
import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }

import dwt.jport.Type
import dwt.jport.translators.ArrayCreationTranslator

class ArrayCreation(node: JdtArrayCreation) extends Expression(node) {
  private val typedDimensions =
    node.dimensions.asInstanceOf[JavaList[JdtExpression]]

  val dimensions = typedDimensions.map(Expression.toJPort(_))
  val elementType = Type.translate(node.getType.getElementType.resolveBinding)
  val initializer = Option(node.getInitializer).map(Expression.toJPort(_))

  override def translate = ArrayCreationTranslator.translate(this)
}
