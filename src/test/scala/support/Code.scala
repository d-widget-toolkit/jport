package support

trait Code {
  def code(code: String): String = trimCode(code)

  def trimCode(code: String) = {
    val firstBlankLine = code.linesWithSeparators.find(_ startsWith " ")
    val margin = firstBlankLine.getOrElse("").takeWhile(_ == ' ').length

    code.linesWithSeparators.map { e =>
      if (e == "\n") e else e.slice(margin, e.length)
    }.mkString.trim
  }

  def javaCode(source: String) = code {
    s"""
    public class Foo {
      public void foo() {
        ${source}
      }
    }
    """
  }

  def dCode(source: String) = code {
    s"""
    class Foo
    {
        void foo()
        {
            ${indent(code(source), 3)}
        }
    }
    """
  }

  private def indent(code: String, level: Int) = {
    val lines = code.linesWithSeparators.buffered
    val prefix = " " * level * 4
    val head = lines.head
    val tail = lines.drop(1)

    head ++ tail.map(e => if (e == "\n") e else prefix + e).mkString
  }
}
