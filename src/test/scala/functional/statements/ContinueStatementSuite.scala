package functional.statements

import functional.support.Suite

class ContinueStatementSuite extends Suite {
  test("basic continue statement") {
    val java = javaCode {
      """
      for(;;) {
        int a = 3;
        continue;
      }
      """
    }

    val d = dCode {
      """
      for (;;)
      {
          int a = 3;

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
        for(;;) {
          int a = 3;
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
                  int a = 3;

                  continue foo;
              }
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
