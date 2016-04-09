package functional.statements

import functional.support.Suite

class ThrowStatementSuite extends Suite {
  test("throw statement") {
    val java = javaCode {
      """
      throw new RuntimeException();
      """
    }

    val throwStatement = dCode {
      """
      throw new RuntimeException();
      """
    }

    val d = "import java.lang.RuntimeException;\n\n" + throwStatement
    java should portFromFileTo("Foo", d)
  }
}
