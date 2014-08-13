package spec.support

import org.scalatest.FunSpec
import org.scalatest.Matchers

abstract class Spec extends FunSpec with Matchers {
	protected def context (desc: String)(block: => Unit) = describe(desc)(block)
}