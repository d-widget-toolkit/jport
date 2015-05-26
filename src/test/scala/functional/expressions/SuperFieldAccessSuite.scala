package functional.expressions

import functional.support.Suite

class SuperFieldAccessSuite extends Suite {
  def newFile = codeToFile("Bar") {
    """
      public class Bar {
        int a;
      }
      """
  }

  test("basic super field access") {
    newFile

    val java = code {
      """
      public class Foo extends Bar {
        public void foo() {
          int a = super.a;
        }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo : Bar
      {
          void foo()
          {
              int a = super.a;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
