package functional

import functional.support.Suite

class FieldDeclarationSuite extends Suite {
  test("class with instance variable") {
    val java = code {
      """
      public class Foo {
        public int a;
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int a;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
