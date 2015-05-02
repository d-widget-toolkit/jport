package spec.dwt.jport.translators

import spec.support.Spec
import spec.support.Parse
import org.eclipse.jdt.core.dom.{ CharacterLiteral => JdtCharacterLiteral }
import org.eclipse.jdt.core.dom.ASTNode
import dwt.jport.ast.expressions.CharacterLiteral
import dwt.jport.translators.CharacterLiteralTranslator

class CharacterLiteralTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    CharacterLiteralTranslator.translate(characterLiteral(node))

  def characterLiteral(node: ASTNode) =
    new CharacterLiteral(node.asInstanceOf[JdtCharacterLiteral])

  describe("CharacterLiteralTranslator") {
    describe("translate") {
      it("translates 'a' to 'a'") {
        translate(parse("'a'")) shouldBe "'a'"
      }

      it("translates character literal with escape sequence") {
        translate(parse("""'\n'""")) shouldBe """'\n'"""
      }
    }
  }
}
