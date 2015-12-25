package functional.statements

import functional.support.Suite

class ReturnStatementSuite extends Suite {
  test("return statement without expression") {
    val java = javaCode("return;")
    val d = dCode("return;")

    java should portFromFileTo("Foo", d)
  }

  test("return statement with expression") {
    val java = code {
      """
      public class Foo {
        public int foo() {
          return 0;
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int foo()
          {
              return 0;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with eternal type in the expression") {
    codeToFile("A")("public class A {}")
    codeToFile("B")("public class B extends A {}")

    val java = code {
      """
      public class Foo {
        public A foo() {
          return new B();
        }
      }
      """
    }

    val d = code {
      """
      import A;
      import B;

      class Foo
      {
          A foo()
          {
              return new B();
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
