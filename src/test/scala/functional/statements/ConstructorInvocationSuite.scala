package functional.statements

import functional.support.Suite

class ConstructorInvocationSuite extends Suite {
  test("basic constructor invocation") {
    val java = code {
      """
      public class Foo {
        public Foo() {}

        public Foo(int a) {
          this();
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          this() {}

          this(int a)
          {
              this();
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with arguments") {
    val java = code {
      """
      public class Foo {
        public Foo() {
          this(3);
        }

        public Foo(int a) {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          this()
          {
              this(3);
          }

          this(int a) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external type") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = code {
      """
      public class Foo {
        public Foo() {
          this(new Bar());
        }

        public Foo(Foo a) {}
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo
      {
          this()
          {
              this(new Bar());
          }

          this(Foo a) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with type parameters") {
    val java = code {
      """
      public class Foo {
        public <T, U> Foo(T a, U b) {}

        public Foo() {
          <Foo, Foo>this(new Foo(), new Foo());
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          this(T, U)(T a, U b) {}

          this()
          {
              this!(Foo, Foo)(new Foo(), new Foo());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("type parameters with external types") {
    codeToFile("Bar")("public class Bar {}")
    codeToFile("Baz")("public class Baz extends Bar {}")

    val java = code {
      """
      public class Foo {
        public <T> Foo(T a) {}

        public Foo() {
          <Bar>this(new Baz());
        }
      }
      """
    }

    val d = code {
      """
      import Bar;
      import Baz;

      class Foo
      {
          this(T)(T a) {}

          this()
          {
              this!(Bar)(new Baz());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
