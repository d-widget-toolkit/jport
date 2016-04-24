package functional.statements

import functional.support.Suite

class SynchronizedStatementSuite extends Suite {
  test("SynchronizedStatementSuite statement with empty body") {
    val java = javaCode {
      """
      synchronized (new Object()) {}
      """
    }

    val d = dCode {
      """
      synchronized (new Object()) {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external type") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode {
      """
      synchronized (new Bar()) {}
      """
    }

    val synchronizedStatement = dCode {
      """
      synchronized (new Bar()) {}
      """
    }

    val d = "import Bar;\n\n" + synchronizedStatement
    java should portFromFileTo("Foo", d)
  }

  test("with body") {
    val java = javaCode {
      """
      synchronized (new Object()) {
        int a = 3;
        int b = 4;
      }
      """
    }

    val d = dCode {
      """
      synchronized (new Object())
      {
          int a = 3;
          int b = 4;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with body and external type") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode {
      """
      synchronized (new Object()) {
        Bar b = new Bar();
        int a = 4;
      }
      """
    }

    val synchronizedStatement = dCode {
      """
      synchronized (new Object())
      {
          Bar b = new Bar();
          int a = 4;
      }
      """
    }

    val d = "import Bar;\n\n" + synchronizedStatement
    java should portFromFileTo("Foo", d)
  }

  test("with single statement body") {
    val java = javaCode {
      """
      synchronized (new Object()) {
        int a = 3;
      }
      """
    }

    val d = dCode {
      """
      synchronized (new Object())
          int a = 3;
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
