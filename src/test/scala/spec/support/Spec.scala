package spec.support

import org.scalatest.FunSpec
import org.scalatest.Matchers
import support.Code

abstract class Spec extends FunSpec with Matchers with Code {
  protected def context(desc: String)(block: => Unit) = describe(desc)(block)
}
