package spec

import org.scalatest.FunSpec
import org.scalatest.Matchers
import spec.support.Spec

class Classes extends Spec
{
  describe("Classes")
  {
    describe("empty, simple class")
    {
      it("sucessfully converts to D")
      {
        "public class Foo {}" should portTo ("class Foo {}")
      }
    }

    describe("with superclass")
    {
      it("sucessfully converts to D")
      {
        "public class Foo extends Bar {}" should portTo ("class Foo : Bar {}")
      }

      describe("and interface")
      {
        it("sucessfully converts to D")
        {
          val java = "public class Foo extends Bar implements IBar {}"
          val d = "class Foo : Bar, IBar {}"

          java should portTo (d)
        }
      }
    }

    describe("with interface")
    {
      it("sucessfully converts to D")
      {
        "public class Foo implements Bar {}" should portTo ("class Foo : Bar {}")
      }

      describe("with multiple interfaces")
      {
        it("sucessfully converts to D")
        {
          val java = "public class Foo implements Bar, Baz {}"
          val d = "class Foo : Bar, Baz {}"

          java should portTo (d)
        }
      }
    }

    describe("with modifiers")
    {
      describe("abstract")
      {
        it("sucessfully converts to D")
        {
          "public abstract class Foo {}" should portTo ("abstract class Foo {}")
        }
      }

      describe("final")
      {
        it("sucessfully converts to D")
        {
          "public final class Foo {}" should portTo ("final class Foo {}")
        }
      }

      describe("access")
      {
        describe("public")
        {
          it("sucessfully converts to D")
          {
            "public class Foo {}" should portTo ("class Foo {}")
          }
        }

        describe("none")
        {
          it("sucessfully converts to D")
          {
            "class Foo {}" should portTo ("package class Foo {}")
          }
        }

        describe("protected")
        {
          it("sucessfully converts to D")
          {
            "protected class Foo {}" should portTo ("protected class Foo {}")
          }
        }

        describe("private")
        {
          it("sucessfully converts to D")
          {
            "private class Foo {}" should portTo ("private class Foo {}")
          }
        }
      }
    }
  }
}
