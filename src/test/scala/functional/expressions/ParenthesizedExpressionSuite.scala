package functional.expressions

import functional.support.Suite

class ParenthesizedExpressionSuite extends Suite {
  test("basic parenthesized expression") {
    val java = javaCode("int a = (3 + 4);")
    val d = dCode("int a = (3 + 4);")

    java should portFromFileTo("Foo", d)
  }

  test("with external type") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = javaCode("Foo a = (new Bar());")
    val d = "import Bar;\n\n" + dCode("Foo a = (new Bar());")

    java should portFromFileTo("Foo", d)
  }

  test("nested") {
    val java = javaCode("int a = ((3 + 4) * 5) * (2 + (4 * 6));")
    val d = dCode("int a = ((3 + 4) * 5) * (2 + (4 * 6));")

    java should portFromFileTo("Foo", d)
  }
}
