package functional.expressions

import functional.support.Suite

class InfixExpressionSuite extends Suite {
  test("string concatenation when the left operand is a string") {
    val java = javaCode("""String a = "foo" + 3;""")
    val d = dCode("""wstring a = "foo" ~ 3;""")

    java should portFromFileTo("Foo", d)
  }

  test("string concatenation when the right operand is a string") {
    val java = javaCode("""String a = 3+ "foo";""")
    val d = dCode("""wstring a = 3 ~ "foo";""")

    java should portFromFileTo("Foo", d)
  }

  test("string concatenation when both operands are strings") {
    val java = javaCode("""String a = "foo" + "bar";""")
    val d = dCode("""wstring a = "foo" ~ "bar";""")

    java should portFromFileTo("Foo", d)
  }
}
