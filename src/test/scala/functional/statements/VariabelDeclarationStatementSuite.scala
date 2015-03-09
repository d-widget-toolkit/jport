package functional.statements

import functional.support.Suite

class VariabelDeclarationStatementSuite extends Suite {
  test("simple statement expression") {
    val java = code {
      """
      public class Foo {
        public void foo () {
          int a = 3;
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo()
          {
              int a = 3;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("without initializer") {
    val java = code {
      """
      public class Foo {
        public void foo () {
          int a;
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo()
          {
              int a;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("infix expression") {
    val java = code {
      """
      public class Foo {
        public void foo () {
          int a = 3 + 3;
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo()
          {
              int a = 3 + 3;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
