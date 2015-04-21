package functional.expressions

import functional.support.Suite

class AssignmentSuite extends Suite {
  test("basic assignment expression") {
    val java = code {
      """
      public class Foo {
        public void foo() {
          int a;
          a = 3;
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo()
          {
              int a;
              a = 3;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
