package functional

import functional.support.Suite
import dwt.jport.JPorter

class MethodDeclarationSuite extends Suite {
  test("method with empty body") {
    val java = code {
      """
      public class Foo {
        public void bar() {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void bar() {}
      }
      """
    }
    java should portFromFileTo("Foo", d)
  }
}
