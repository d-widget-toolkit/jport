package functional.support

import org.scalatest.FunSuite
import org.scalatest.Matchers

import dwt.jport.JPorter

class Suite extends FunSuite with Matchers {
  def portTo(expected: String) = be(expected).
    compose((java: String) => JPorter.port(java))

  def code(code: String): String = trimCode(code).trim

  def trimCode(code: String) = {
    val firstBlankLine = code.linesWithSeparators.find(_ startsWith " ")
    val margin = firstBlankLine.getOrElse("").takeWhile(_ == ' ').length

    code.linesWithSeparators.map { e =>
      if (e == "\n") e else e.slice(margin, e.length)
    }.mkString
  }
}
