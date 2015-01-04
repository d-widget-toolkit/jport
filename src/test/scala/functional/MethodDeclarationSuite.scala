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
          void bar (T)() {}
      }
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
