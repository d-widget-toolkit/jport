package functional.statements

import functional.support.Suite

class ContinueStatementSuite extends Suite {
  test("basic continue statement") {
    val java = javaCode("for(;;) { continue; }")

    val d = dCode {
      """
      for (;;)
      {
          continue;
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
          continue foo;
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
                  continue foo;
              }
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
