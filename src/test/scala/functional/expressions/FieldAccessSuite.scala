package functional.expressions

import functional.support.Suite

class FieldAccessSuite extends Suite {
  test("basic field access expression") {
    val java = code {
      """
      public class Foo {
        public int a;

        public void bar() {
          int b = this.a;
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int a;

          void bar()
          {
              int b = this.a;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("field access in method invocation") {
    val java = code {
      """
      public class Foo {
        public int a;

        public Foo foo() {
          return new Foo();
        }

        public void bar() {
          int b = foo().a;
        }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int a;

          Foo foo()
          {
          }

          void bar()
          {
              int b = foo().a;
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("with external type") {
    codeToFile("Bar")("public class Bar { int a; }")

    val java = javaCode("int a = new Bar().a;")
    val d = "import Bar;\n\n" + dCode("int a = new Bar().a;")

    java should portFromFileTo("Foo", d)
  }
}
