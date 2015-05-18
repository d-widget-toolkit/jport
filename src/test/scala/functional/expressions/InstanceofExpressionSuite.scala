package functional.expressions

import functional.support.Suite

class InstanceofExpressionSuite extends Suite {
  test("basic instanceof expression") {
    val java = javaCode("boolean a = new Foo() instanceof Foo;")
    val d = dCode("bool a = cast(Foo) new Foo();")

    java should portFromFileTo("Foo", d)
  }

  test("with external type for the right operand") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = javaCode("boolean a = new Foo() instanceof Bar;")
    val d = "import Bar;\n\n" + dCode("bool a = cast(Bar) new Foo();")

    java should portFromFileTo("Foo", d)
  }

  test("with external type for the left operand") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = javaCode("boolean a = new Bar() instanceof Foo;")
    val d = "import Bar;\n\n" + dCode("bool a = cast(Foo) new Bar();")

    java should portFromFileTo("Foo", d)
  }
}
