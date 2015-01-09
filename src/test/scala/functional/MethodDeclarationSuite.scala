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

  test("method with illegal D identifier") {
    val java = code {
      """
      public class Foo {
        public void out() {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void out_() {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("method without body") {
    val java = code {
      """
      public interface Foo {
        public void bar();
      }
      """
    }

    val d = code {
      """
      interface Foo
      {
          void bar();
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  // formatting
  test("formatting of multiple method") {
    val java = code {
      """
      public abstract class Foo {
        public abstract void a();
        public abstract void b();

        public void c() {
          int a = 3;
        }

        public void d() {}
        public void e() {}
      }
      """
    }

    val d = code {
      """
      abstract class Foo
      {
          abstract void a();
          abstract void b();

          void c()
          {
          }

          void d() {}
          void e() {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("method followed by instance variable") {
    val java = code {
      """
      public class Foo {
        public void foo() {}

        public int a;
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo() {}

          int a;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("parameters") {
    val java = code {
      """
      public class Foo {
        public void bar(int a, int b) {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void bar(int a, int b) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("parameters with illegal D identifiers") {
    val java = code {
      """
      public class Foo {
        public void bar(int in, int out) {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void bar(int in_, int out_) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("parameters with illegal D types") {
    codeToFile("in")("public class in {}")
    codeToFile("out")("public class out {}")

    val java = code {
      """
      public class Foo {
        public void bar(in a, out b) {}
      }
      """
    }

    val d = code {
      """
      import in_;
      import out_;

      class Foo
      {
          void bar(in_ a, out_ b) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("final modifier") {
    val java = code {
      """
      public class Foo {
        public final void bar() {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          final void bar() {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("no modifier") {
    val java = code {
      """
      public class Foo {
        void bar() {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          /* package */ void bar() {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  // imports
  test("method with external return type") {
    codeToFile("Bar")("public class Bar {}")

    val java = code {
      """
      public class Foo {
        public Bar bar() { return null; }
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo
      {
          Bar bar()
          {
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("method with external parameter types") {
    codeToFile("Bar")("public class Bar {}")

    val java = code {
      """
      public class Foo {
        public void bar(Bar a) {}
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo
      {
          void bar(Bar a) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  // type parameters
  test("method with type parameter") {
    val java = code {
      """
      public class Foo {
        public <T> void bar() {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void bar(T)() {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("method with type parameter as return type") {
    val java = code {
      """
      public class Foo {
        public <T> T bar() { return null; }
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          T bar(T)()
          {
          }
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("method with type parameter as parameter type") {
    val java = code {
      """
      public class Foo {
        public <T> void bar(T t) {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void bar(T)(T t) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("external type as bound for type parameter") {
    codeToFile("Bar")("public class Bar {}")

    val java = code {
      """
      public class Foo {
        public <T extends Bar> void bar(T t) {}
      }
      """
    }

    val d = code {
      """
      import Bar;

      class Foo
      {
          void bar(T : Bar)(T t) {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("methods in multiple classes in the same file") {
    val java = code {
      """
      public class Foo {
        public void foo() {}
      }

      class Bar {
        void bar() {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          void foo() {}
      }

      package class Bar
      {
          /* package */ void bar() {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
