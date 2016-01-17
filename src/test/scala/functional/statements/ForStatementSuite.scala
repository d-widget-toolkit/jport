
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

  test("external type as initializer") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode("for(Bar a = new Bar();;) {}")
    val d = "import Bar;\n\n" + dCode("for (Bar a = new Bar();;) {}")

    java should portFromFileTo("Foo", d)
  }

  test("for statement with initializer and expression") {
    val java = javaCode("for(int a = 0; a < 10;) {}")
    val d = dCode("for (int a = 0; a < 10;) {}")

    java should portFromFileTo("Foo", d)
  }

  test("initializer and external type in expression") {
    codeToFile("Bar")("public class Bar { int a; }")

    val java = javaCode("for(int a = 0; new Bar().a < 10;) {}")
    val forStatement = dCode("for (int a = 0; new Bar().a < 10;) {}")

    val d = "import Bar;\n\n" + forStatement
    java should portFromFileTo("Foo", d)
  }

  test("for statement with initializer, expression and updater") {
    val java = javaCode("for(int a = 0; a < 10; a++) {}")
    val d = dCode("for (int a = 0; a < 10; a++) {}")

    java should portFromFileTo("Foo", d)
  }

  test("initializer, expression and external type in updater") {
    codeToFile("Bar")("public class Bar { int a; }")

    val java = javaCode("for(int a = 0; a < 10; new Bar().a++) {}")
    val forStatement = dCode("for (int a = 0; a < 10; new Bar().a++) {}")

    val d = "import Bar;\n\n" + forStatement
    java should portFromFileTo("Foo", d)
  }

  test("with body") {
    val java = javaCode {
      """
      for(int a = 0; a < 10; a++)
      {
        int b = 0;
      }
      """
    }

    val d = dCode {
      """
      for (int a = 0; a < 10; a++)
      {
          int b = 0;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with body and external type") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode {
      """
      for(int a = 0; a < 10; a++)
      {
        Bar b = new Bar();
      }
      """
    }

    val forStatement = dCode {
      """
      for (int a = 0; a < 10; a++)
      {
          Bar b = new Bar();
      }
      """
    }

    val d = "import Bar;\n\n" + forStatement
    java should portFromFileTo("Foo", d)
  }

  test("with next node") {
    val java = javaCode {
      """
      for(int a = 0; a < 10; a++)
      {
        int b = 0;
      }

      int c = 0;
      """
    }

    val d = dCode {
      """
      for (int a = 0; a < 10; a++)
      {
          int b = 0;
      }

      int c = 0;
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
