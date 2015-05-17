package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ FieldAccess => JdtFieldAccess }
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.FieldAccessTranslator

class FieldAccess(node: JdtFieldAccess) extends Expression(node) {
  val expression = node.getExpression.toJPort
  val name = node.getName.toJPort

  override def translate = FieldAccessTranslator.translate(this)
}
