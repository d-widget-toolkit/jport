package functional.statements

import functional.support.Suite

class DoStatementSuite extends Suite {
  test("do statement without body") {
    val java = javaCode("do; while(true);")
    val d = dCode("do {} while(true);")

    java should portFromFileTo("Foo", d)
  }

  test("do statement with empty body") {
    val java = javaCode("do {} while(true);")
    val d = dCode("do {} while(true);")

    java should portFromFileTo("Foo", d)
  }

  test("external type as expression") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode("do; while(new Bar() != null );")
    val d = "import Bar;\n\n" + dCode("do {} while(new Bar() != null);")

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
      do
      {
        int a = 0;
      } while(true);
      """
    }

    val d = dCode {
      """
      do
      {
          int a = 0;
      } while(true);
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with body and external type") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode {
      """
      do
      {
        Bar a = new Bar();
      } while(true);
      """
    }

    val doStatement = dCode {
      """
      do
      {
          Bar a = new Bar();
      } while(true);
      """
    }

    val d = "import Bar;\n\n" + doStatement
    java should portFromFileTo("Foo", d)
  }

  test("with next node") {
    val java = javaCode {
      """
      do
      {
        int a = 0;
      } while(false);

      int b = 0;
      """
    }

    val d = dCode {
      """
      do
      {
          int a = 0;
      } while(false);

      int b = 0;
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
