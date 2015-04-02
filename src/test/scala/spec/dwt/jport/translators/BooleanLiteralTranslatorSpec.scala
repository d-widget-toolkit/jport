package spec.dwt.jport.translators

import dwt.jport.ast.expressions.BooleanLiteral
import org.eclipse.jdt.core.dom.AST
import spec.support.Spec
import dwt.jport.translators.BooleanLiteralTranslator

class BooleanLiteralTranslatorSpec extends Spec {
  val ast = AST.newAST(AST.JLS8)

  def translate(value: BooleanLiteral) = BooleanLiteralTranslator.translate(value)
  def literal(value: Boolean) = new BooleanLiteral(ast.newBooleanLiteral(value))

  describe("BooleanLiteralTranslator") {
    describe("translate") {
      it("""translates true to "true"""") {
        translate(literal(true)) shouldBe "true"
      }

      it("""translates false to "false"""") {
        translate(literal(false)) shouldBe "false"
      }
    }
  }
}
