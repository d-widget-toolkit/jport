package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ NullLiteral => JdtNullLiteral }

import dwt.jport.ast.expressions.NullLiteral
import dwt.jport.translators.NullLiteralTranslator

import spec.support.Parse
import spec.support.Spec

class NullLiteralTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    NullLiteralTranslator.translate(arrayAccess(node))

  def arrayAccess(node: ASTNode) =
    new NullLiteral(node.asInstanceOf[JdtNullLiteral])

  describe("NullLiteralTranslator") {
    describe("translate") {
      it("""translates a null literal to "null"""") {
        translate(parse("null")) shouldBe "null"
      }
    }
  }
}
