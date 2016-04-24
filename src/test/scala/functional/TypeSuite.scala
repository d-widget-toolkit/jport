package functional

import functional.support.Suite

class TypeSuite extends Suite {
  test("array type") {
    val java = javaCode("Object[] table = (Object[]) new Object();")
    val d = dCode("Object[] table = cast(Object[]) new Object();")

    java should portFromFileTo("Foo", d)
  }

  test("returning array with primitive element type") {
    val java = code {
      """
      public class Foo {
        public char[] foo() {
          return new char[1];
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          char[] foo()
          {
              return new char[1];
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
