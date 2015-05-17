package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ ThisExpression => JdtThisExpression }

import spec.support.Parse
import spec.support.Spec

import dwt.jport.translators.ThisExpressionTranslator
import dwt.jport.ast.expressions.ThisExpression

class ThisExpressionTranslatorSpece extends Spec with Parse {
  def translate(node: ASTNode) =
    ThisExpressionTranslator.translate(thisExpression(node))

  def thisExpression(node: ASTNode) =
    new ThisExpression(node.asInstanceOf[JdtThisExpression])

  describe("ThisExpressionTranslator") {
    describe("translate") {
      it("""translates a this expression to "this"""") {
        translate(parse("this")) shouldBe "this"
      }
    }
  }
}
