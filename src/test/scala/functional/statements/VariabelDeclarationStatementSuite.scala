package functional.statements

import functional.support.Suite

class VariabelDeclarationStatementSuite extends Suite {
  test("simple statement expression") {
    val java = javaCode("int a = 3;")
    val d = dCode("int a = 3;")

    java should portFromFileTo("Foo", d)
  }

  test("without initializer") {
    val java = javaCode("int a;")
    val d = dCode("int a;")

    java should portFromFileTo("Foo", d)
  }

  test("infix expression") {
    val java = javaCode("int a = 3 + 3;")
    val d = dCode("int a = 3 + 3;")

    java should portFromFileTo("Foo", d)
  }
}
