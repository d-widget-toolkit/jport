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
        "class Foo {}" shouldPortTo "class Foo {}"
      }
    }

    describe("with superclass")
    {
      it("sucessfully converts to D")
      {
        "class Foo extends Bar {}" shouldPortTo "class Foo : Bar {}"
      }

      describe("and interface")
      {
        it("sucessfully converts to D")
        {
          val java = "class Foo extends Bar implements IBar {}"
          val d = "class Foo : Bar, IBar {}"

          java shouldPortTo d
        }
      }
    }

    describe("with interface")
    {
      it("sucessfully converts to D")
      {
        "class Foo implements Bar {}" shouldPortTo "class Foo : Bar {}"
      }

      describe("with multiple interfaces")
      {
        it("sucessfully converts to D")
        {
          val java = "class Foo implements Bar, Baz {}"
          val d = "class Foo : Bar, Baz {}"

          java shouldPortTo d
        }
      }
    }
  }
}
