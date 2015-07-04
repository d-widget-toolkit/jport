package functional.statements

import functional.support.Suite

class SuperConstructorInvocationSuite extends Suite {
  test("basic super constructor invocation") {
    codeToFile("Bar")("public class Bar { Bar() {} }")

    val java = code {
      """
      public class Foo extends Bar {
        public Foo() {
          super();
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo : Bar
      {
          this()
          {
              super();
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with arguments") {
    codeToFile("Bar")("public class Bar { Bar(int a) {} }")

    val java = code {
      """
      public class Foo extends Bar {
        public Foo() {
          super(3);
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo : Bar
      {
          this()
          {
              super(3);
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external type") {
    codeToFile("Baz")("public class Baz {}")
    codeToFile("Bar")("public class Bar { Bar(Baz a) {} }")

    val java = code {
      """
      public class Foo extends Bar {
        public Foo() {
          super(new Baz());
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
          this()
          {
              super(new Baz());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with type parameters") {
    codeToFile("Bar")("public class Bar { public <T, U> Bar(T a, U b) {} }")

    val java = code {
      """
      public class Foo extends Bar {
        public Foo() {
          <Foo, Foo>super(new Foo(), new Foo());
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo : Bar
      {
          this()
          {
              super!(Foo, Foo)(new Foo(), new Foo());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("type parameters with external types") {
    codeToFile("Bar")("public class Bar { public <T> Bar(T a) {} }")
    codeToFile("Baz")("public class Baz extends Bar {}")

    val java = code {
      """
      public class Foo extends Bar {
        public Foo() {
          <Bar>super(new Baz());
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
          this()
          {
              super!(Bar)(new Baz());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
