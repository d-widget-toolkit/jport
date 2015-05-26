package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ SuperFieldAccess => JdtSuperFieldAccess }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.SuperFieldAccessTranslator

class SuperFieldAccess(node: JdtSuperFieldAccess) extends Expression(node) {
  val name = node.getName.toJPort

  def translate = SuperFieldAccessTranslator.translate(this)
}
