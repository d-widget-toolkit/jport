package functional.expressions

import functional.support.Suite

class SuperMethodInvocationSuite extends Suite {
  test("basic super method invocation") {
    codeToFile("Bar") {
      """
      public class Bar {
        public void bar() {}
      }
      """
    }

    val java = code {
      """
      public class Foo extends Bar {
        public void foo() {
          super.bar();
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo : Bar
      {
          void foo()
          {
              super.bar();
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with arguments") {
    codeToFile("Bar") {
      """
      public class Bar {
        public void bar(int a, int b) {}
      }
      """
    }

    val java = code {
      """
      public class Foo extends Bar {
        public void foo() {
          super.bar(3, 4);
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo : Bar
      {
          void foo()
          {
              super.bar(3, 4);
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external types") {
    codeToFile("Base")("public class Base {}")
    codeToFile("Sub")("public class Sub extends Base {}")

    codeToFile("Bar") {
      """
      public class Bar {
        public void bar(Base a, Base b) {}
      }
      """
    }

    val java = code {
      """
      public class Foo extends Bar {
        public void foo() {
          super.bar(new Base(), new Sub());
        }
      }
      """
    }

    val d = code {
      """
      import Bar;
      import Base;
      import Sub;

      class Foo : Bar
      {
          void foo()
          {
              super.bar(new Base(), new Sub());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with type parameters") {
    codeToFile("Bar") {
      """
      public class Bar {
        public <T, U> void bar(T a, U b) {}
      }
      """
    }

    val java = code {
      """
      public class Foo extends Bar {
        public void foo() {
          super.<Foo, Foo>bar(new Foo(), new Foo());
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo : Bar
      {
          void foo()
          {
              super.bar!(Foo, Foo)(new Foo(), new Foo());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("type parameters with external types") {
    codeToFile("Baz")("public class Baz {}")

    codeToFile("Bar") {
      """
      public class Bar {
        public <T> void bar(T a) {}
      }
      """
    }

    val java = code {
      """
      public class Foo extends Bar {
        public void foo() {
          this.<Baz>bar(null);
        }
      }
      """
    }

    val d = code {
      """
      import Bar;
      import Baz;

      class Foo : Bar
      {
          void foo()
          {
              this.bar!(Baz)(null);
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
