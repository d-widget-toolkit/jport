package functional

import functional.support.Suite
import dwt.jport.UnhandledException

class InitializerSuite extends Suite {
  test("static initializer") {
    val java = code {
      """
        public class Foo {
          public static int a;

          static {
            a = 3;
          }
        }
        """
    }

    val d = code {
      """
        class Foo
        {
            static int a;

            shared static this()
            {
                a = 3;
            }
        }
        """
    }

    java should portFromFileTo("Foo", d)
  }

  test("initializer - cannot handle") {
    val java = code {
      """
      public class Foo {
        public static int a;

        {
          a = 3;
        }
      }
      """
    }

    an[UnhandledException] should be thrownBy { port(java, "Foo") }
  }
}
