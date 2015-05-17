package functional.expressions

import functional.support.Suite

class MethodInvocationSuite extends Suite {
  test("basic method invocation") {
    val java = javaCode("foo();")
    val d = dCode("foo();")

    java should portFromFileTo("Foo", d)
  }
}
