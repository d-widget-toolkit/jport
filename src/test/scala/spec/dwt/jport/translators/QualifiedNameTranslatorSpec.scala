package spec.dwt.jport.translators

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ QualifiedName => JdtQualifiedName }

import dwt.jport.ast.expressions.QualifiedName
import dwt.jport.translators.QualifiedNameTranslator

import spec.support.Parse
import spec.support.Spec

class QualifiedNameTranslatorSpec extends Spec with Parse {
  def translate(node: ASTNode) =
    QualifiedNameTranslator.translate(qualifiedName(node))

  def qualifiedName(node: ASTNode) =
    new QualifiedName(node.asInstanceOf[JdtQualifiedName])

  describe("QualifiedNameTranslator") {
    describe("translate") {
      it("translates a qualified name") {
        translate(parse("a.b.c")) shouldBe "a.b.c"
      }

      it("appends an underscore to illegal D identifiers") {
        translate(parse("out.in")) shouldBe "out_.in_"
      }
    }
  }
}
