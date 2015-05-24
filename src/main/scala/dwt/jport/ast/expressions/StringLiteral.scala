package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ StringLiteral => JdtStringLiteral }

import dwt.jport.translators.StringLiteralTranslator

class StringLiteral(node: JdtStringLiteral) extends Expression(node) {
  val escapedValue = node.getEscapedValue

  def translate = StringLiteralTranslator.translate(this)
}
