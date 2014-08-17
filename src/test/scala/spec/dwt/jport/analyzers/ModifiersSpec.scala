package spec.dwt.jport.analyzers

import spec.support.Spec

import org.eclipse.jdt.core.dom.Modifier
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.AST

import dwt.jport.analyzers.Modifiers
import dwt.jport.core.JPortAny._

class ModifiersSpec extends Spec {
  import Modifier.ModifierKeyword._
  import Modifier.ModifierKeyword
  import Modifiers.convert

  def newModifier(keyword: Modifier.ModifierKeyword): Modifier = {
    val cls = Class.forName("org.eclipse.jdt.core.dom.Modifier")
    val con = cls.getDeclaredConstructors()(0).tap(_.setAccessible(true))
    con.setAccessible(true)

    con.newInstance(AST.newAST(AST.JLS8)).asInstanceOf[Modifier].tap { m =>
      m.setKeyword(keyword)
    }
  }

  def convertTo(expected: String) = be(expected).
    compose { (keywords: Any) =>
      val ks = if (keywords.isInstanceOf[Array[ModifierKeyword]])
        keywords.asInstanceOf[Array[ModifierKeyword]].map(newModifier(_))

      else
        Array(newModifier(keywords.asInstanceOf[Modifier.ModifierKeyword]))

      Modifiers.convert(ks)
    }

  describe("Modifiers") {
    describe("convert") {
      it("converts a list of IExtendedModifier to a string of D modifiers") {
        val modifiers = Array(PRIVATE_KEYWORD, FINAL_KEYWORD, SYNCHRONIZED_KEYWORD)
        modifiers should convertTo("private final synchronized")
      }

      it("converts 'abstract' to 'package abstract'") {
        ABSTRACT_KEYWORD should convertTo("package abstract")
      }

      it("converts 'final' to 'package final'") {
        FINAL_KEYWORD should convertTo("package final")
      }

      it("converts 'static' to 'package static'") {
        STATIC_KEYWORD should convertTo("package static")
      }

      context("access modifiers") {
        it("converts 'private' to 'private'") {
          PRIVATE_KEYWORD should convertTo("private")
        }

        it("converts 'protected' to 'protected'") {
          PROTECTED_KEYWORD should convertTo("protected")
        }

        it("converts 'public' to an empty string, since 'public' is the default in D") {
          PUBLIC_KEYWORD should convertTo("")
        }

        context("no access modifier") {
          it("converts to the 'package' modifier") {
            val modifiers = new Array[IExtendedModifier](0)
            convert(modifiers) shouldBe "package"
          }

          context("with other modifiers") {
            it("converts the modifiers and prepends the 'package' access modifier") {
              FINAL_KEYWORD should convertTo("package final")
            }
          }
        }

        context("access modifier last") {
          it("puts the access modifier first") {
            Array(FINAL_KEYWORD, PRIVATE_KEYWORD) should convertTo("private final")
          }
        }

        context("when virtual method") {
          def convert(modifiers: Iterable[IExtendedModifier]) =
            Modifiers.convert(modifiers, true)

          it("converts 'private' to 'private'") {
            PRIVATE_KEYWORD should convertTo("private")
          }

          it("converts 'protected' to 'protected'") {
            PROTECTED_KEYWORD should convertTo("protected")
          }

          it("converts 'public' to an empty string") {
            PUBLIC_KEYWORD should convertTo("")
          }

          context("no access modifier") {
            it("adds a '/* package */' comment, since package is not virtual in D") {
              val modifiers = new Array[IExtendedModifier](0)
              convert(modifiers) shouldBe "/* package */"
            }
          }
        }
      }
    }
  }
}
