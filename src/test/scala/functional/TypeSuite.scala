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
        public int[] foo() {
          return new int[1];
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int[] foo()
          {
              return new int[1];
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("'char' is translated to 'wchar'") {
    val java = javaCode("char a;")
    val d = dCode("wchar a;")

    java should portFromFileTo("Foo", d)
  }

  test("'Class' in java.lang is translated to 'ClassInfo'") {
    val java = javaCode("Class a;")
    val d = dCode("ClassInfo a;")

    java should portFromFileTo("Foo", d)
  }
}
