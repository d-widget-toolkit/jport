package functional.statements

import functional.support.Suite

class WhileStatementSuite extends Suite {
  test("while statement without body") {
    val java = javaCode("while (true);")
    val d = dCode("while (true) {}")

    java should portFromFileTo("Foo", d)
  }

  test("while statement with empty body") {
    val java = javaCode("while (true) {}")
    val d = dCode("while (true) {}")

    java should portFromFileTo("Foo", d)
  }

  test("external type as expression") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode("while (new Bar() != null );")
    val d = "import Bar;\n\n" + dCode("while (new Bar() != null) {}")

    java should portFromFileTo("Foo", d)
  }

  test("for statement with initializer and expression") {
    val java = javaCode("for(int a = 0; a < 10;) {}")
    val d = dCode("for (int a = 0; a < 10;) {}")

    java should portFromFileTo("Foo", d)
  }

  test("with body") {
    val java = javaCode {
      """
      while (true)
      {
        int a = 0;
      }
      """
    }

    val d = dCode {
      """
      while (true)
      {
          int a = 0;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with body and external type") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode {
      """
      while (true)
      {
        Bar a = new Bar();
      }
      """
    }

    val doStatement = dCode {
      """
      while (true)
      {
          Bar a = new Bar();
      }
      """
    }

    val d = "import Bar;\n\n" + doStatement
    java should portFromFileTo("Foo", d)
  }

  test("with next node") {
    val java = javaCode {
      """
      int a = 3;

      while (a != 3)
      {
        int b = 0;
      }

      int c = 0;
      """
    }

    val d = dCode {
      """
      int a = 3;

      while (a != 3)
      {
          int b = 0;
      }

      int c = 0;
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
