package dwt.jport.ast.expressions

import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.{ ArrayInitializer => JdtArrayInitializer }
import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import dwt.jport.translators.ArrayInitializerTranslator

class ArrayInitializer(node: JdtArrayInitializer) extends Expression(node) {
  private val typedExpressions =
    node.expressions.asInstanceOf[JavaList[JdtExpression]]

  val expressions = typedExpressions.map(Expression.toJPort(_))

  override def translate = ArrayInitializerTranslator.translate(this)
}
