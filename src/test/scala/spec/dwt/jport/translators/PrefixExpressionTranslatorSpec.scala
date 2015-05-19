package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ PrefixExpression => JdtPrefixExpression }

import dwt.jport.ast.expressions.PrefixExpression
import dwt.jport.translators.PrefixExpressionTranslator

import spec.support.Parse
import spec.support.Spec

class PrefixExpressionTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    PrefixExpressionTranslator.translate(postfixExpression(node))

  def postfixExpression(node: ASTNode) =
    new PrefixExpression(node.asInstanceOf[JdtPrefixExpression])

  describe("PrefixExpressionTranslator") {
    describe("translate") {
      it("""translates "++a" to "++a"""") {
        translate(parse("++a")) shouldBe "++a"
      }

      it("""translates "--a" to "--a"""") {
        translate(parse("--a")) shouldBe "--a"
      }
    }
  }
}
