package functional

import functional.support.Suite

class FieldDeclarationSuite extends Suite {
  test("class with field declaration") {
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

  test("field declaration with illegal D identifier") {
    val java = code {
      """
      public class Foo {
        public int out;
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int out_;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("multiple variables in single declaration") {
    val java = code {
      """
      public class Foo {
        public int a, b, c, out;
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int a, b, c, out_;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("no modifier") {
    val java = code {
      """
      public class Foo {
        int a;
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          package int a;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("final modifier for primitive type") {
    val java = code {
      """
      public class Foo {
        public final int a = 0;
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          const int a = 0;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  // formatting
  test("two field declarations") {
    val java = code {
      """
      public class Foo {
        public int a;
        public int b;
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int a;
          int b;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("field declarations followed by method") {
    val java = code {
      """
      public class Foo {
        public int a;

        public void foo() {}
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          int a;

          void foo() {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("grouped field declarations") {
    val java =
      """
      public class Foo {
        public int a;
        public int b;

        public int c;
        public
int d;

        public int e;
        public int f;
      }
      """

    val d = code {
      """
      class Foo
      {
          int a;
          int b;

          int c;
          int d;

          int e;
          int f;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("field declaration with simple initializer") {
    val java = code {
      """
      public class Foo {
        public final int a = 1;
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          const int a = 1;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("'final' for String is translated to 'const'") {
    val java = code {
      """
      public class Foo {
        public final String a = "foo";
      }
      """
    }

    val d = code {
      """
      class Foo
      {
          const String a = "foo";
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
