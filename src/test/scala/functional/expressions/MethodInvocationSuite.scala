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
}
