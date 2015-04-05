package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ ArrayAccess => JdtArrayAccess }

import dwt.jport.ast.expressions.ArrayAccess
import dwt.jport.translators.ArrayAccessTranslator

import spec.support.Parse
import spec.support.Spec

class ArrayAccessTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    ArrayAccessTranslator.translate(arrayAccess(node))

  def arrayAccess(node: ASTNode) =
    new ArrayAccess(node.asInstanceOf[JdtArrayAccess])

  describe("ArrayAccessTranslator") {
    describe("translate") {
      it("translates an array access expression to D") {
        translate(parse("a[1]")) shouldBe "a[1]"
      }
    }
  }
}
