package dwt.jport.features.support.matchers

import org.scalatest.matchers.Matcher
import org.scalatest.matchers.MatchResult

trait PortToMatcher
{
  class PortToMatcher(dCode: String) extends Matcher[String]
  {
    def apply (javaCode: String) = {
      MatchResult(
        dCode == javaCode,
        s"The Java code $javaCode did not port to $dCode",
        s"The Java code $javaCode did port to $dCode"
      )
    }
  }

  def portTo (dCode: String) = new PortToMatcher(dCode)
}

object PortToMatcher extends PortToMatcher
