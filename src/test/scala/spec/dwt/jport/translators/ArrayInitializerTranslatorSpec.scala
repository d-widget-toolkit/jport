package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ ArrayInitializer => JdtArrayInitializer }

import dwt.jport.ast.expressions.ArrayInitializer
import dwt.jport.translators.ArrayInitializerTranslator

import spec.support.Parse
import spec.support.Spec

class ArrayInitializerTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    ArrayInitializerTranslator.translate(arrayAccess(node))

  def arrayAccess(node: ASTNode) =
    new ArrayInitializer(node.asInstanceOf[JdtArrayInitializer])

  describe("ArrayInitializerTranslator") {
    describe("translate") {
      it("translates initializer") {
        translate(parse("{1, 2}")) shouldBe "[1, 2]"
      }

      it("translates nested initializer") {
        translate(parse("{1, {2, 3}}")) shouldBe "[1, [2, 3]]"
      }

      it("translates initializer without initializer list") {
        translate(parse("{}")) shouldBe "[]"
      }
    }
  }
}
