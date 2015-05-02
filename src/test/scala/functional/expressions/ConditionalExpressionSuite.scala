package functional.expressions

import functional.support.Suite

class ConditionalExpressionSuite extends Suite {
  test("basic conditional expression") {
    val java = javaCode("int a = true ? 1 : 0;")
    val d = dCode("int a = true ? 1 : 0;")

    java should portFromFileTo("Foo", d)
  }

  test("external types") {
    codeToFile("A")("public class A {}")
    codeToFile("B")("public class B {}")
    codeToFile("C")("public class C {}")

    val java = javaCode("Object foo = new A() == null ? new B(): new C();")

    val imports = Array("import A", "import B", "import C").mkString(";\n")
    val d = imports + ";\n\n" +
      dCode("Object foo = new A() == null ? new B() : new C();")

    java should portFromFileTo("Foo", d)
  }
}
