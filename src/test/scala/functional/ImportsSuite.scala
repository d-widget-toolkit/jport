package functional

import functional.support.Suite
import dwt.jport.JPorter

class ImportsSuite extends Suite {
  test("imports") {
    codeToFile("A")("public class A {}")
    val java = code("public class Foo extends A {}")

    val d = code {
      """
      import A;

      class Foo : A {}
      """
    }

    java should portFromFileTo("Foo", d)
  }
}