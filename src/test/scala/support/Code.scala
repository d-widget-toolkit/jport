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
}