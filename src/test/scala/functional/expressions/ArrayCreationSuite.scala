package functional.expressions

import functional.support.Suite

class ArrayCreationSuite extends Suite {
  test("simple array creation expression") {
    val java = javaCode("int[] a = new int[3];")
    val d = dCode("int[] a = new int[3];")

    java should portFromFileTo("Foo", d)
  }

  test("array creation expression with initializer") {
    val java = javaCode("int[] a = new int[]{3, 4};")
    val d = dCode("int[] a = [3, 4];")

    java should portFromFileTo("Foo", d)
  }

  test("multi-dimension array creation expression") {
    val java = javaCode("int[][] a = new int[3][4];")
    val d = dCode("int[][] a = new int[][](3, 4);")

    java should portFromFileTo("Foo", d)
  }

  test("array creation expression with user defined type") {
    codeToFile("Bar")("public class Bar {}")

    val java = javaCode("Bar[] a = new Bar[3];")
    val d = "import Bar;\n\n" + dCode("Bar[] a = new Bar[3];")

    java should portFromFileTo("Foo", d)
  }
}
