package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ ArrayCreation => JdtArrayCreation }
import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.ITypeBinding

import dwt.jport.ITypeBindigImplicits._
import dwt.jport.Type
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.ArrayCreationTranslator

class ArrayCreation(node: JdtArrayCreation) extends Expression(node) {
  private val typedDimensions =
    node.dimensions.asInstanceOf[JavaList[JdtExpression]]

  val dimensions = typedDimensions.map(Expression.toJPort(_))
  val elementType = Type.translate(node.getType.getElementType.resolveBinding)
  val initializer = Option(node.getInitializer).map(Expression.toJPort(_))

  override lazy val importTypeBindings: Seq[ITypeBinding] =
    Array(node.resolveTypeBinding.canonicalType)

  override def translate = ArrayCreationTranslator.translate(this)
}
