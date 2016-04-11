package spec.dwt.jport.writers

import org.scalatest.BeforeAndAfterEach

import dwt.jport.writers.ImportWriter
import dwt.jport.DCoder

import spec.support.Spec
import support.Code

class ImportWriterSpec extends Spec with BeforeAndAfterEach {
  var writer: ImportWriter = null

  def result = DCoder.dcoder.result
  def wpackage = writer.importsWPackage
  def wopackage = writer.importsWOPackage

  override def beforeEach = {
    writer = new ImportWriter
    DCoder.dcoder.reset()
  }

  describe("ImportWriter") {
    describe("importsWPackage") {
      it("returns a list of imports with a package qualifier") {
        writer :+ "foo.bar"
        wpackage shouldBe Array("foo.bar")
      }

      context("imports with both package qualifier and without") {
        it("only retuns a list of the imports with a package") {
          writer :+ Array("foo", "foo.bar")
          wpackage shouldBe Array("foo.bar")
        }
      }

      context("with multiple imports") {
        it("returns a sorted list") {
          writer :+ Array("x.y", "foo.bar", "a.b")
          wpackage shouldBe Array("a.b", "foo.bar", "x.y")
        }
      }

      context("when import list contains 'java.lang.Object'") {
        it("does not include 'java.lang.Object'") {
          writer :+ Array("x.y", "foo.bar", "java.lang.Object", "a.b")
          wpackage shouldBe Array("a.b", "foo.bar", "x.y")
        }
      }

      context("when import list contains 'java.lang.Exception'") {
        it("does not include 'java.lang.Exception'") {
          writer :+ Array("x.y", "foo.bar", "java.lang.Exception", "a.b")
          wpackage shouldBe Array("a.b", "foo.bar", "x.y")
        }
      }

      context("when import list contains 'java.lang.Throwable'") {
        it("does not include 'java.lang.Exception'") {
          writer :+ Array("x.y", "foo.bar", "java.lang.Throwable", "a.b")
          wpackage shouldBe Array("a.b", "foo.bar", "x.y")
        }
      }
    }

    describe("importsWOPackage") {
      it("returns a list of imports without a package qualifier") {
        writer :+ "bar"
        wopackage shouldBe Array("bar")
      }

      context("imports with both package qualifier and without") {
        it("only retuns a list of the imports without a package") {
          writer :+ Array("foo", "foo.bar")
          wopackage shouldBe Array("foo")
        }
      }

      context("with multiple imports") {
        it("returns a sorted list") {
          writer :+ Array("x", "foo", "a")
          wopackage shouldBe Array("a", "foo", "x")
        }
      }
    }

    describe("write") {
      context("imports without a package qualifier") {
        it("writes a sorted list of imports statemens") {
          writer :+ Array("foo", "bar")
          writer.write()

          result shouldBe code {
            """
            import bar;
            import foo;
            """
          }
        }
      }

      context("imports with a package qualifier") {
        it("writes grouped sorted list of imports statemens") {
          writer :+ Array("foo.a", "bar.a")
          writer.write()

          result shouldBe code {
            """
            import bar.a;

            import foo.a;
            """
          }
        }
      }

      context("with duplicated imports") {
        it("writes all unique imports") {
          writer :+ Array("foo", "bar", "foo")
          writer.write()

          result shouldBe code {
            """
            import bar;
            import foo;
            """
          }
        }
      }

      context("imports with and without a package qualifier") {
        it("writes grouped sorted list of imports statemens, packageless last") {
          writer :+ Array("asd", "foo.a", "bar.a")
          writer.write()

          result shouldBe code {
            """
            import bar.a;

            import foo.a;

            import asd;
            """
          }
        }
      }

      context("multiple imports with the same package qualifier") {
        it("groupes the imports with the same package qualifier togehter") {
          writer :+ Array("b", "a", "foo.a", "foo.b", "foo.c", "bar.b", "bar.a")
          writer.write()

          result shouldBe code {
            """
            import bar.a;
            import bar.b;

            import foo.a;
            import foo.b;
            import foo.c;

            import a;
            import b;
            """
          }
        }
      }

      context("multiple imports with nested package qualifier") {
        it("groupes the imports with the same package qualifier togehter") {
          writer :+ Array(
            "b",
            "org.eclipse.swt.widgets.Button",
            "org.eclipse.swt.widgets.Window",
            "java.lang.String",
            "a",
            "org.eclipse.swt.widgets.Control",
            "org.eclipse.jdt.core.dom.CompilationUnit")

          writer.write()

          result shouldBe code {
            """
            import java.lang.String;

            import org.eclipse.jdt.core.dom.CompilationUnit;
            import org.eclipse.swt.widgets.Button;
            import org.eclipse.swt.widgets.Control;
            import org.eclipse.swt.widgets.Window;

            import a;
            import b;
            """
          }
        }
      }
    }

    describe("toString") {
      it("writes foobar baz") {
        writer :+ Array("foo.bar.Baz")
        writer.write()

        writer.toString shouldBe "import foo.bar.Baz;\n\n"
      }
    }
  }
}
