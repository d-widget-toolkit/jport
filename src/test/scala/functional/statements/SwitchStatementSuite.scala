package functional.statements

import functional.support.Suite

class SwitchStatementSuite extends Suite {
  test("switch statement") {
    val java = javaCode {
      """
      switch (0) {
        case 0:
          break;
      }
      """
    }

    val d = dCode {
      """
      switch (0)
      {
          case 0:
              break;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with default default label") {
    val java = javaCode {
      """
      switch (0) {
        case 0:
          int a = 3;
        default:
          break;
      }
      """
    }

    val d = dCode {
      """
      switch (0)
      {
          case 0:
              int a = 3;

          default:
              break;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with multiple cases labels") {
    val java = javaCode {
      """
      switch (0) {
        case 0:
          int a = 3;
        case 1:
          int b = 4;
      }
      """
    }

    val d = dCode {
      """
      switch (0)
      {
          case 0:
              int a = 3;

          case 1:
              int b = 4;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with multiple statements") {
    val java = javaCode {
      """
      switch (0) {
        case 0:
          int a = 3;
          break;
      }
      """
    }

    val d = dCode {
      """
      switch (0)
      {
          case 0:
              int a = 3;
              break;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
