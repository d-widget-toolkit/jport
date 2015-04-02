package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ BooleanLiteral => JdtBooleanLiteral }

import dwt.jport.translators.BooleanLiteralTranslator

class BooleanLiteral(node: JdtBooleanLiteral) extends Expression(node) {
  override def toString = node.toString
  override def translate = BooleanLiteralTranslator.translate(this)
}
