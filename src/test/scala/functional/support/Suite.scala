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
    var margin = firstBlankLine.getOrElse("").takeWhile(_ == ' ').length

    if (margin > 0)
      margin -= 1

    code.linesWithSeparators.map(e => e.slice(margin, e.length)).mkString.trim
  }
}
