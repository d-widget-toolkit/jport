package dwt.jport.features.support

import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import org.scalatest.FeatureSpecLike
import org.scalatest.Matchers
import dwt.jport.core.JPortAny.anyToJPortAny
import dwt.jport.JPorter

class ShouldPortToMatcher(val javaCode: String) extends FeatureSpec with GivenWhenThen with Matchers
{
  def shouldPortTo (expected: String) = {
    Given("The following Java source code:")
    markup(javaCode)

    When("I run JPort on the source code")
    val result = JPorter.port(javaCode)

    Then("the output should be:")
    markup(expected)

    result shouldBe expected
  }
}

abstract class Spec extends FeatureSpec with GivenWhenThen with Matchers
{
  implicit def stringToPortToMatcher (javaCode: String) = {
    new ShouldPortToMatcher(javaCode)
  }

  def code (code: String): String = trimCode(code).tap(markup(_)).trim

  def trimCode (code: String) = {
    val firstBlankLine = code.linesWithSeparators.find(_ startsWith " ")
    var margin = firstBlankLine.getOrElse("").takeWhile(_ == ' ').length

    if (margin > 0)
      margin -= 1

    code.linesWithSeparators.map(e => e.slice(margin, e.length)).mkString
  }
}