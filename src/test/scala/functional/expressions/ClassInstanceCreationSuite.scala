package functional.expressions

import functional.support.Suite

class ClassInstanceCreationSuite extends Suite {
  test("basic class instance creation expression") {

    val java = javaCode("Foo foo = new Foo();")
    val d = dCode("Foo foo = new Foo();")

    java should portFromFileTo("Foo", d)
  }

  test("subtyping and user defined types") {
    codeToFile("Bar")("public class Bar extends Foo {}")

    val java = javaCode("Foo foo = new Bar();")
    val d = "import Bar;\n\n" + dCode("Foo foo = new Bar();")

    java should portFromFileTo("Foo", d)
  }

  test("class instance creation with arguments") {
    val java = code {
      """
      public class Foo {
        public Foo(int a, int b) {}

        public void foo() {
          Foo foo = new Foo(1, 2);
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          this(int a, int b) {}

          void foo()
          {
              Foo foo = new Foo(1, 2);
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
