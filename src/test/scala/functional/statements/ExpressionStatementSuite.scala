package functional.statements

import functional.support.Suite

class ExpressionStatementSuite extends Suite {
  test("simple statement expression") {
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
