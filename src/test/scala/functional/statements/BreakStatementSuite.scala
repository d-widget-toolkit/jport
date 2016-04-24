package functional.statements

import functional.support.Suite

class BreakStatementSuite extends Suite {
  test("basic break statement") {
    val java = javaCode {
      """
      for(;;) {
        int a = 3;
        break;
      }
      """
    }

    val d = dCode {
      """
      for (;;)
      {
          int a = 3;
          break;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with label") {
    val java = javaCode {
      """
      foo:
        for(;;) {
          int a = 3;
          break foo;
        }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo()
          {
          foo:
              for (;;)
              {
                  int a = 3;
                  break foo;
              }
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
