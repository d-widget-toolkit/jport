package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.AST

import dwt.jport.ast.expressions.NumberLiteral
import dwt.jport.translators.NumberLiteralTranslator

import spec.support.Spec

class NumberLiteralTranslatorSpec extends Spec {
  val ast = AST.newAST(AST.JLS8)

  def translate(value: NumberLiteral) = NumberLiteralTranslator.translate(value)
  def literal(value: String) = new NumberLiteral(ast.newNumberLiteral(value))

  describe("NumberLiteralTranslator") {
    describe("translate") {
      it("translates an integer literal") {
        translate(literal("1")) shouldBe "1"
      }

      it("translates a double literal") {
        translate(literal("1.0")) shouldBe "1.0"
      }

      it("translates a float literal") {
        translate(literal("1.0f")) shouldBe "1.0f"
      }

      it("translates a hexadecimal literal") {
        translate(literal("0x1")) shouldBe "0x1"
      }

      it("translates a binary literal") {
        translate(literal("0b10")) shouldBe "0b10"
      }

      it("translates an integer literal containing underscores") {
        translate(literal("10_10_10")) shouldBe "10_10_10"
      }
    }
  }
}
