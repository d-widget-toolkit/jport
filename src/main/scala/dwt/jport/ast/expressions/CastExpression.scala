package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ CastExpression => JdtCastExpression }
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.CastExpressionTranslator
import dwt.jport.Type

class CastExpression(node: JdtCastExpression) extends Expression(node) {
  val expression = node.getExpression.toJPort
  private val typeBinding = node.getType.resolveBinding
  val typ = Type.translate(typeBinding)

  override lazy val importTypeBindings =
    expression.importTypeBindings :+ typeBinding

  override def translate = CastExpressionTranslator.translate(this)
}
