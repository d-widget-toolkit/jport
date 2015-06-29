package spec.dwt.jport

import dwt.jport.Symbol

import spec.support.Spec

class SymbolSpec extends Spec {
  describe("Symbol") {
    describe("translate") {
      it("translates a Java identifier to D identifier") {
        Symbol.translate("a") shouldBe "a"
      }

      context("when the identifier is not a legal D identifier") {
        it("appends an underscore to the identifier") {
          Symbol.translate("out") shouldBe "out_"
        }

        context("when the identifier is part of a fully qualified name") {
          it("does not append any underscores") {
            Symbol.translate("in.out") shouldBe "in.out"
          }
        }
      }
    }
  }
}
