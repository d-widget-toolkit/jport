package functional.expressions

import functional.support.Suite

class ClassInstanceCreationSuite extends Suite {
  test("basic class instance creation expression") {

    val java = javaCode("Foo foo = new Foo();")
    val d = dCode("Foo foo = new Foo();")

    java should portFromFileTo("Foo", d)
  }

  test("subtyping and user defined types") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = javaCode("Foo foo = new Bar();")
    val d = "import Bar;\n\n" + dCode("Foo foo = new Bar();")

    java should portFromFileTo("Foo", d)
  }
}
