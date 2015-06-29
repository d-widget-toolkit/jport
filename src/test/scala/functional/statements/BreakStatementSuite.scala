package functional.statements

import functional.support.Suite

class BreakStatementSuite extends Suite {
  test("basic break statement") {
    val java = javaCode("for(;;) { break; }")

    val d = dCode {
      """
      for (;;)
      {
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
        for(;;)
        {
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
                  break foo;
              }
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
