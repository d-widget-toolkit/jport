package functional.statements

import functional.support.Suite

class TryStatementSuite extends Suite {
  test("try statement with finally block") {
    val java = javaCode {
      """
      try {}
      finally {}
      """
    }

    val d = dCode {
      """
      try {}

      finally {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with finally block and non-empty blocks") {
    val java = javaCode {
      """
      try {
        int a = 3;
      }
      finally {
        int b = 4;
      }
      """
    }

    val d = dCode {
      """
      try
      {
          int a = 3;
      }

      finally
      {
          int b = 4;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with single catch clause") {
    val java = javaCode {
      """
      try {}
      catch (Exception e) {}
      """
    }

    val d = dCode {
      """
      try {}

      catch (Exception e) {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with single catch clause and non-emoty blocks") {
    val java = javaCode {
      """
      try {
        int a = 3;
      }
      catch (Exception e) {
        int b = 4;
      }
      """
    }

    val d = dCode {
      """
      try
      {
          int a = 3;
      }

      catch (Exception e)
      {
          int b = 4;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with multiple catch clause and non-emoty blocks") {
    val java = javaCode {
      """
        try {
          int a = 3;
        }
        catch (Exception e) {
          int b = 4;
        }
        catch (Throwable e) {
          int c = 5;
        }
        """
    }

    val d = dCode {
      """
        try
        {
            int a = 3;
        }

        catch (Exception e)
        {
            int b = 4;
        }

        catch (Throwable e)
        {
            int c = 5;
        }
        """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external exception") {
    codeToFile("BarException")("public class BarException extends Exception {}")

    val java = javaCode {
      """
        try {
          throw new BarException();
        }
        catch (BarException e) {}
        """
    }

    val tryStatement = dCode {
      """
        try
        {
            throw new BarException();
        }

        catch (BarException e) {}
        """
    }

    val d = "import BarException;\n\n" + tryStatement
    java should portFromFileTo("Foo", d)
  }
}
