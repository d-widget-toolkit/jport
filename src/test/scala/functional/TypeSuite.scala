package functional

import functional.support.Suite

class TypeSuite extends Suite {
  test("array type") {
    val java = javaCode("Object[] table = (Object[]) new Object();")
    val d = dCode("Object[] table = cast(Object[]) new Object();")

    java should portFromFileTo("Foo", d)
  }
}
