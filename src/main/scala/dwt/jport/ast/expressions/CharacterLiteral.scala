package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ CharacterLiteral => JdtCharacterLiteral }

import dwt.jport.translators.CharacterLiteralTranslator

class CharacterLiteral(node: JdtCharacterLiteral) extends Expression(node) {
  val escapedValue = node.getEscapedValue

  override def translate = CharacterLiteralTranslator.translate(this)
}
