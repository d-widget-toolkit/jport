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
}
