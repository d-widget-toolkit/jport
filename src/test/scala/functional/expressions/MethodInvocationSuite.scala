package functional.expressions

import functional.support.Suite

class MethodInvocationSuite extends Suite {
  test("basic method invocation") {
    val java = javaCode("foo();")
    val d = dCode("foo();")

    java should portFromFileTo("Foo", d)
  }

  test("with arguments") {
    val java = code {
      """
      public class Foo {
        public void foo(int a, int b) {}
        public void bar() {
          foo(3, 4);
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo(int a, int b) {}

          void bar()
          {
              foo(3, 4);
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external types") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = code {
      """
      public class Foo {
        public void foo(Foo a, Foo b) {}
        public void bar() {
          foo(new Foo(), new Bar());
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo
      {
          void foo(Foo a, Foo b) {}

          void bar()
          {
              foo(new Foo(), new Bar());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with type parameters") {
    val java = code {
      """
      public class Foo {
        public <T, U> void foo(T a, U b) {}
        public void bar() {
          this.<Foo, Foo>foo(new Foo(), new Foo());
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo(T, U)(T a, U b) {}

          void bar()
          {
              this.foo!(Foo, Foo)(new Foo(), new Foo());
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("type parameters with external types") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = code {
      """
      public class Foo {
        public <T> void foo(T a) {}
        public void bar() {
          this.<Bar>foo(null);
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo
      {
          void foo(T)(T a) {}

          void bar()
          {
              this.foo!(Bar)(null);
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
