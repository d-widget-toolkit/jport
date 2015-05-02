package functional.expressions

import functional.support.Suite

class AssignmentSuite extends Suite {
  test("basic assignment expression") {
    val java = code {
      """
      public class Foo {
        public void foo() {
          int a;
          a = 3;
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
              a = 3;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("subtyping") {
    codeToFile("Bar")("public class Bar {}")
    codeToFile("Baz")("public class Baz extends Bar {}")

    val java = code {
      """
      public class Foo {
        public void foo() {
          Bar a;
          a = new Baz();
        }
      }
      """
    }

    val d = code {
      """
      import Bar;
      import Baz;

      class Foo
      {
          void foo()
          {
              Bar a;
              a = new Baz();
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
