package functional.statements

import functional.support.Suite

class TypeDeclarationStatementSuite extends Suite {
  test("local class") {
    val java = javaCode {
      """
      class Bar {}
      """
    }

    val d = dCode {
      """
      class Bar {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("local class with method declarations") {
    val java = javaCode {
      """
      class Bar {
        public void foo(int a) {}
        public void bar(int a) {}
      }
      """
    }

    val d = dCode {
      """
      class Bar
      {
          void foo(int a) {}
          void bar(int a) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
