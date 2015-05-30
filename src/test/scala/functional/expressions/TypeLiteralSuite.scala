package functional.expressions

import functional.support.Suite

class TypeLiteralSuite extends Suite {
  test("basic type literal") {
    val java = javaCode("Class a = Foo.class;")
    val d = dCode("ClassInfo a = Foo.classinfo;")

    java should portFromFileTo("Foo", d)
  }

  test("with external type") {
    codeToFile("Bar")("public class Bar")

    val java = javaCode("Class a = Bar.class;")
    val d = "import Bar;\n\n" + dCode("ClassInfo a = Bar.classinfo;")

    java should portFromFileTo("Foo", d)
  }
}
