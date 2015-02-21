package spec.dwt.jport.translators

import org.scalatest.BeforeAndAfter
import dwt.jport.translators.Constant
import spec.support.Spec
import dwt.jport.UnhandledException

class ConstantSpec extends Spec with BeforeAndAfter {
  def translate(value: Object) = Constant.translate(value)

  describe("Constant") {
    describe("translate") {
      it("""translates a String object, "foo", to "\"foo\""""") {
        translate("foo") shouldBe "\"foo\""
      }

      it("""translates an Integer(1) object to "1"""") {
        translate(new Integer(1)) shouldBe "1"
      }

      it("""translates a Double(1.0) object to "1.0"""") {
        translate(new java.lang.Double(1.0)) shouldBe "1.0"
      }

      it("""translates a Float(1.0f) object to "1.0f"""") {
        translate(new java.lang.Float(1.0f)) shouldBe "1.0f"
      }

      it("""translates a Boolean(false) object to "false"""") {
        translate(new java.lang.Boolean(false)) shouldBe "false"
      }

      it("""translates a Boolean(true) object to "true"""") {
        translate(new java.lang.Boolean(true)) shouldBe "true"
      }

      it("throws an exception for unhandled types") {
        a[UnhandledException] should be thrownBy (translate(new Object))
      }
    }
  }
}
