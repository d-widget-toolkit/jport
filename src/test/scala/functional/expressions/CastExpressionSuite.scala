package functional.expressions

import functional.support.Suite

class CastExpressionSuite extends Suite {
  test("basic cast expression") {
    val java = javaCode("int a = (int)1.0;")
    val d = dCode("int a = cast(int) 1.0;")

    java should portFromFileTo("Foo", d)
  }

  test("upcast with external expression type") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = javaCode("Foo a = (Foo) new Bar();")
    val d = "import Bar;\n\n" + dCode("Foo a = cast(Foo) new Bar();")

    java should portFromFileTo("Foo", d)
  }

  test("upcast with external type") {
    codeToFile("Bar")("public class Bar extends Foo {}")
    codeToFile("Baz")("public class Baz extends Bar {}")

    val java = javaCode("Foo a = (Bar)new Baz();")
    val d = "import Bar;\nimport Baz;\n\n" +
      dCode("Foo a = cast(Bar) new Baz();")

    java should portFromFileTo("Foo", d)
  }
}
