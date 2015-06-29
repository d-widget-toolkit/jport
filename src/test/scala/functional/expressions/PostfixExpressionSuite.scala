package functional.expressions

import functional.support.Suite

class PostfixExpressionSuite extends Suite {
  test("basic postfix expression") {
    val java = javaCode {
      """
      int a = 0;
      int b = a++;
      """
    }

    val d = dCode {
      """
      int a = 0;
      int b = a++;
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external type") {
    codeToFile("Bar")("public class Bar { int a; }")

    val java = javaCode("int a = new Bar().a++;")
    val d = "import Bar;\n\n" + dCode("int a = new Bar().a++;")

    java should portFromFileTo("Foo", d)
  }
}
