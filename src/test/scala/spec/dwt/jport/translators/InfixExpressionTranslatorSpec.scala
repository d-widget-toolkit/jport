package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }

import dwt.jport.ast.expressions.InfixExpression
import dwt.jport.translators.InfixExpressionTranslator

import spec.support.Parse
import spec.support.Spec

class InfixExpressionTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    InfixExpressionTranslator.translate(InfixExpression(node))

  def InfixExpression(node: ASTNode) =
    new InfixExpression(node.asInstanceOf[JdtInfixExpression])

  describe("InfixExpressionTranslator") {
    describe("translate") {
      context("when the operator is ==") {
        context("and the left operand is 'null'") {
          it("translates the operator to 'is'") {
            translate(parse("null == a")) shouldBe "null is a"
          }
        }

        context("and the right operand is 'null'") {
          it("translates the operator to 'is'") {
            translate(parse("a == null")) shouldBe "a is null"
          }
        }

        context("and both operators are identifiers") {
          it("translates the operator to '=='") {
            translate(parse("a == b")) shouldBe "a == b"
          }
        }
      }

      context("when the operator is !=") {
        context("and the left operand is 'null'") {
          it("translates the operator to '!is'") {
            translate(parse("null != a")) shouldBe "null !is a"
          }
        }

        context("and the right operand is 'null'") {
          it("translates the operator to '!is'") {
            translate(parse("a != null")) shouldBe "a !is null"
          }
        }

        context("and both operators are identifiers") {
          it("translates the operator to '!='") {
            translate(parse("a != b")) shouldBe "a != b"
          }
        }
      }
    }
  }
}
