package functional.statements

import functional.support.Suite

class IfStatementSuite extends Suite {
  test("if statement without body") {
    val java = javaCode("if (true);")
    val d = dCode("if (true) {}")

    java should portFromFileTo("Foo", d)
  }

  test("if statement with empty body") {
    val java = javaCode("if (true){}")
    val d = dCode("if (true) {}")

    java should portFromFileTo("Foo", d)
  }

  test("external type in expression") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode("if (new Bar() != null) {}")
    val d = "import Bar;\n\n" + dCode("if (new Bar() != null) {}")

    java should portFromFileTo("Foo", d)
  }

  test("with else statement with no body") {
    val java = javaCode {
      """
      if (true) {}
      else;
      """
    }

    val d = dCode {
      """
      if (true) {}

      else {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with else statement with empty body") {
    val java = javaCode {
      """
      if (true) {}
      else {}
      """
    }

    val d = dCode {
      """
      if (true) {}

      else {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with body") {
    val java = javaCode {
      """
      if (true)
      {
        int a = 3;
        int b = 4;
      }
      """
    }

    val d = dCode {
      """
      if (true)
      {
          int a = 3;
          int b = 4;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with body and next node") {
    val java = javaCode {
      """
      if (true)
      {
        int a = 3;
        int b = 4;
      }

      int c = 0;
      """
    }

    val d = dCode {
      """
      if (true)
      {
          int a = 3;
          int b = 4;
      }

      int c = 0;
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with else statement with body") {
    val java = javaCode {
      """
      if (true) {}
      else
      {
        int a = 3;
        int b = 4;
      }
      """
    }

    val d = dCode {
      """
      if (true) {}

      else
      {
          int a = 3;
          int b = 4;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with else statement with body and next node") {
    val java = javaCode {
      """
      if (true) {}
      else
      {
        int a = 3;
        int b = 4;
      }

      int c = 0;
      """
    }

    val d = dCode {
      """
      if (true) {}

      else
      {
          int a = 3;
          int b = 4;
      }

      int c = 0;
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with body and external type") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode {
      """
      if (true)
      {
        Bar a = new Bar();
        int b = 4;
      }
      """
    }

    val ifStatement = dCode {
      """
      if (true)
      {
          Bar a = new Bar();
          int b = 4;
      }
      """
    }

    val d = "import Bar;\n\n" + ifStatement
    java should portFromFileTo("Foo", d)
  }

  test("with else statement with body and external type") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode {
      """
      if (true) {}
      else
      {
        Bar a = new Bar();
        int b = 4;
      }
      """
    }

    val ifStatement = dCode {
      """
      if (true) {}

      else
      {
          Bar a = new Bar();
          int b = 4;
      }
      """
    }

    val d = "import Bar;\n\n" + ifStatement
    java should portFromFileTo("Foo", d)
  }

  test("with single statement body") {
    val java = javaCode {
      """
      int a;

      if (false)
        a = 3;
      else
        a = 4;
      """
    }

    val d = dCode {
      """
      int a;

      if (false)
          a = 3;

      else
          a = 4;
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with single statement body and next node") {
    val java = javaCode {
      """
      int a;
      if (false)
        a = 3;
      int b = 4;
      """
    }

    val d = dCode {
      """
      int a;

      if (false)
          a = 3;

      int b = 4;
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with single statement body no next node") {
    val java = javaCode {
      """
      int a;
      if (true)
        a = 3;
      """
    }

    val d = dCode {
      """
      int a;

      if (true)
          a = 3;
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with single statement block body and next node") {
    val java = javaCode {
      """
      int a = 1;
      if (true) {
        a = 2;
      }
      a = 3;
      """
    }

    val d = dCode {
      """
      int a = 1;

      if (true)
          a = 2;

      a = 3;
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("nested") {
    val java = javaCode {
      """
      if ((1 & 2) != 0) {
        int a = 3;
        if ((1 & 2) != 0) {
          int b = 4;
        }
      }
      """
    }

    val d = dCode {
      """
      if ((1 & 2) != 0)
      {
          int a = 3;

          if ((1 & 2) != 0)
              int b = 4;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("single line if with multiline else") {
    val java = javaCode {
      """
      if (true) {
        int a = 3;
      }
      else {
        int b = 4;
        int c = 5;
      }
      """
    }

    val d = dCode {
      """
      if (true)
          int a = 3;

      else
      {
          int b = 4;
          int c = 5;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("multiple then and else statements") {
    val java = javaCode {
      """
      if (true) {
        int d = 6;
        int a = 3;
      }
      else {
        int b = 4;
        int c = 5;
      }
      """
    }

    val d = dCode {
      """
      if (true)
      {
          int d = 6;
          int a = 3;
      }

      else
      {
          int b = 4;
          int c = 5;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("single line if and single line else with adjacent single line statement") {
    val java = javaCode {
      """
      int a = 1;
      if (true) {
        a = 2;
      }
      else {
        a = 3;
      }
      a = 4;
      """
    }

    val d = dCode {
      """
      int a = 1;

      if (true)
          a = 2;

      else
          a = 3;

      a = 4;
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
