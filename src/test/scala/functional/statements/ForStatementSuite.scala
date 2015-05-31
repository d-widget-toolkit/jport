package functional.statements

import functional.support.Suite

class ForStatementSuite extends Suite {
  test("for statement without body") {
    val java = javaCode("for(;;);")
    val d = dCode("for (;;) {}")

    java should portFromFileTo("Foo", d)
  }

  test("for statement with empty body") {
    val java = javaCode("for(;;) {}")
    val d = dCode("for (;;) {}")

    java should portFromFileTo("Foo", d)
  }

  test("for statement with initializer and empty body") {
    val java = javaCode("for(int a = 0;;) {}")
    val d = dCode("for (int a = 0;;) {}")

    java should portFromFileTo("Foo", d)
  }
}
