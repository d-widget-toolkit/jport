package functional

import functional.support.Suite
import dwt.jport.JPorter

class ClassesSuite extends Suite {
  test("empty, simple class") {
    "public class Foo {}" should portTo("class Foo {}")
  }

  // superclass and interface
  test("superclass") {
    "public class Foo extends Bar {}" should portTo("class Foo : Bar {}")
  }

  test("superclass and interface") {
    val java = "public class Foo extends Bar implements IBar {}"
    val d = "class Foo : Bar, IBar {}"

    java should portTo(d)
  }

  test("interface") {
    "public class Foo implements Bar {}" should portTo("class Foo : Bar {}")
  }

  test("multiple interfaces") {
    val java = "public class Foo implements Bar, Baz {}"
    val d = "class Foo : Bar, Baz {}"

    java should portTo(d)
  }

  // modifiers
  test("abstract modifier") {
    "public abstract class Foo {}" should portTo("abstract class Foo {}")
  }

  test("final modifier") {
    "public final class Foo {}" should portTo("final class Foo {}")
  }

  test("public access modifier") {
    "public class Foo {}" should portTo("class Foo {}")
  }

  test("no modifier") {
    "class Foo {}" should portTo("package class Foo {}")
  }

  test("protected access modifier") {
    "protected class Foo {}" should portTo("protected class Foo {}")
  }

  test("private access modifier") {
    "private class Foo {}" should portTo("private class Foo {}")
  }

  // type parameters
  test("class with type parameter") {
    "public class Foo<T> {}" should portTo("class Foo (T) {}")
  }

  test("class with multiple type parameters") {
    "public class Foo<A, B, C> {}" should portTo("class Foo (A, B, C) {}")
  }

  test("class with bounded type parameter") {
    val java = code {
      """
      public class A {}
      public class Foo<T extends A> {}
      """
    }

    val d = code {
      """
      class A {}

      class Foo (T : A) {}
      """
    }

    java should portTo(d)
  }

  test("class with multiple bounded type parameters") {
    val java = code {
      """
      public class A {}
      public class Foo<T extends A, U extends A> {}
      """
    }

    val d = code {
      """
      class A {}

      class Foo (T : A, U : A) {}
      """
    }

    java should portTo(d)
  }

  test("class with type parameter with multiple bounds") {
    val java = code {
      """
      public class A {}
      public interface B {}
      public class Foo<T extends A & B> {}
      """
    }

    val d = code {
      """
      class A {}

      interface B {}

      class Foo (T) if (is(T : A) && is(T : B)) {}
      """
    }

    java should portTo(d)
  }

  test("class with multiple type parameters with multiple bounds") {
    val java = code {
      """
      public class A {}
      public interface B {}
      public class Foo<T extends A & B, U extends A & B> {}
      """
    }

    val d = code {
      """
      class A {}

      interface B {}

      class Foo (T, U) if (is(T : A) && is(T : B) && is(U : A) && is(U : B)) {}
      """
    }

    java should portTo(d)
  }

  test("class with mixed bounded type parameters") {
    val java = code {
      """
      public class A {}
      public interface B {}
      public class Foo<T, U extends A, W extends A & B> {}
      """
    }

    val d = code {
      """
      class A {}

      interface B {}

      class Foo (T, U : A, W) if (is(W : A) && is(W : B)) {}
      """
    }

    java should portTo(d)
  }
}
