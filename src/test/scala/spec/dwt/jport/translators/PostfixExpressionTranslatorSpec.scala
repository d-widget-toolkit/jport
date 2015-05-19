package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ PostfixExpression => JdtPostfixExpression }

import dwt.jport.ast.expressions.PostfixExpression
import dwt.jport.translators.PostfixExpressionTranslator

import spec.support.Parse
import spec.support.Spec

class PostfixExpressionTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    PostfixExpressionTranslator.translate(postfixExpression(node))

  def postfixExpression(node: ASTNode) =
    new PostfixExpression(node.asInstanceOf[JdtPostfixExpression])

  describe("PostfixExpressionTranslator") {
    describe("translate") {
      it("""translates "a++" to "a++"""") {
        translate(parse("a++")) shouldBe "a++"
      }

      it("""translates "a--" to "a--"""") {
        translate(parse("a--")) shouldBe "a--"
      }
    }
  }
}
