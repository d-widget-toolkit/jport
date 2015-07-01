package functional.statements

import functional.support.Suite

class LabeledStatementSuite extends Suite {
  test("basic labeled statement") {
    val java = javaCode("foo: for(;;) {}")

    val d = code {
      """
      class Foo
      {
          void foo()
          {
          foo:
              for (;;) {}
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external type in statement") {
    codeToFile("Bar")("public class Bar {}")
    val java = javaCode("foo: for(Bar a = new Bar();;) {}")

    val statement = code {
      """
      class Foo
      {
          void foo()
          {
          foo:
              for (Bar a = new Bar();;) {}
          }
      }
      """
    }

    val d = "import Bar;\n\n" + statement
    java should portFromFileTo("Foo", d)
  }
}
