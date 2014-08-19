package functional

import functional.support.Suite
import dwt.jport.JPorter

class TypeDeclarationsSuite extends Suite {
  test("empty, simple class") {
    "public class Foo {}" should portFromFileTo("Foo", "class Foo {}")
  }

  // superclass and interface
  test("superclass") {
    codeToFile("Bar")("public class Bar {}")
    val java = "public class Foo extends Bar {}"

    java should portFromFileTo("Foo", "class Foo : Bar {}")
  }

  test("superclass and interface") {
    codeToFile("Bar")("public class Bar {}")
    codeToFile("IBar")("public interface IBar {}")
    val java = "public class Foo extends Bar implements IBar {}"

    val d = "class Foo : Bar, IBar {}"

    java should portFromFileTo("Foo", d)
  }

  test("interface") {
    codeToFile("Bar")("public interface Bar {}")
    val java = "public class Foo implements Bar {}"

    java should portFromFileTo("Foo", "class Foo : Bar {}")
  }

  test("multiple interfaces") {
    codeToFile("Bar")("public interface Bar {}")
    codeToFile("Baz")("public interface Baz {}")
    val java = "public class Foo implements Bar, Baz {}"

    val d = "class Foo : Bar, Baz {}"

    java should portFromFileTo("Foo", d)
  }

  // modifiers
  test("abstract modifier") {
    val java = "public abstract class Foo {}"
    java should portFromFileTo("Foo", "abstract class Foo {}")
  }

  test("final modifier") {
    val java = "public final class Foo {}"
    java should portFromFileTo("Foo", "final class Foo {}")
  }

  test("public access modifier") {
    "public class Foo {}" should portFromFileTo("Foo", "class Foo {}")
  }

  test("no modifier") {
    "class Foo {}" should portFromFileTo("Foo", "package class Foo {}")
  }

  // type parameters
  test("class with type parameter") {
    "public class Foo<T> {}" should portFromFileTo("Foo", "class Foo (T) {}")
  }

  test("class with multiple type parameters") {
    val java = "public class Foo<A, B, C> {}"
    java should portFromFileTo("Foo", "class Foo (A, B, C) {}")
  }

  test("class with bounded type parameter") {
    codeToFile("A")("public class A {}")
    val java = code("public class Foo<T extends A> {}")

    val d = code {
      """
      class Foo (T : A) {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("class with multiple bounded type parameters") {
    codeToFile("A")("public class A {}")
    val java = code("public class Foo<T extends A, U extends A> {}")

    val d = code {
      """
      class Foo (T : A, U : A) {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("class with type parameter with multiple bounds") {
    codeToFile("A")("public class A {}")
    codeToFile("B")("public interface B {}")
    val java = code("public class Foo<T extends A & B> {}")

    val d = code {
      """
      class Foo (T) if (is(T : A) && is(T : B)) {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("class with multiple type parameters with multiple bounds") {
    codeToFile("A")("public class A {}")
    codeToFile("B")("public interface B {}")
    val java = code("public class Foo<T extends A & B, U extends A & B> {}")

    val d = code {
      """
      class Foo (T, U) if (is(T : A) && is(T : B) && is(U : A) && is(U : B)) {}
      """
    }

    java should portFromFileTo("Foo", d)
  }

  test("class with mixed bounded type parameters") {
    codeToFile("A")("public class A {}")
    codeToFile("B")("public interface B {}")
    val java = code("public class Foo<T, U extends A, W extends A & B> {}")

    val d = code {
      """
      class Foo (T, U : A, W) if (is(W : A) && is(W : B)) {}
      """
    }

    java should portFromFileTo("Foo", d)
  }
}
