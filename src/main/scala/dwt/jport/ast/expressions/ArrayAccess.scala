package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ ArrayAccess => JdtArrayAccess }

import dwt.jport.translators.ArrayAccessTranslator

class ArrayAccess(node: JdtArrayAccess) extends Expression(node) {
  val array = Expression.toJPort(node.getArray)
  val index = Expression.toJPort(node.getIndex)

  override def translate = ArrayAccessTranslator.translate(this)
}
