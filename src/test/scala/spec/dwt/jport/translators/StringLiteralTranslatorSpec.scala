package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ StringLiteral => JdtStringLiteral }

import dwt.jport.ast.expressions.StringLiteral
import dwt.jport.translators.StringLiteralTranslator

import spec.support.Parse
import spec.support.Spec

class StringLiteralTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    StringLiteralTranslator.translate(stringLiteral(node))

  def stringLiteral(node: ASTNode) =
    new StringLiteral(node.asInstanceOf[JdtStringLiteral])

  describe("StringLiteralTranslator") {
    describe("translate") {
      it("translates a string literal") {
        translate(parse(""""foo"""")) shouldBe """"foo""""
      }

      it("translates a string literal with an escape sequence") {
        translate(parse(""""fo\no"""")) shouldBe """"fo\no""""
      }
    }
  }
}
