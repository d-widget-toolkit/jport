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

  test("instance variable with illegal D identifier") {
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

  test("final modifier") {
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
          const int a;
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }

  // formatting
  test("two instance variables") {
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

  test("instance variables followed by method") {
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
}
