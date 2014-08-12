package dwt.jport.features

import dwt.jport.JPorter
import dwt.jport.features.support.Spec
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.MatchResult

//import dwt.jport.features.support.matchers.PortToMatcher._

class Classes extends Spec
{ 
  feature("Classes")
  {
    /*scenario("Simple class")
    {
      Given("The following Java source code:")
      val java = code
      {
        """
        class Foo {}
        """
      }

      When("I run JPort on the source code")
      val d: String = JPorter.port(java)

      Then("the output should be:")
      val expected: String = code
      {
        """
        class Foo {}
        """
      }

      d shouldBe expected
    }*/

    scenario("Class with a superclass")
    {
      Given("The following Java source code:")
      val java = code
      {
        """
        class Bar {}
        """
      }

      val d = code
      {
        """
        class Bar {}
        """
      }

      java shouldPortTo d
    }
  }
}