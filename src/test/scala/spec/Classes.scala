package spec

import org.scalatest.FunSpec
import org.scalatest.Matchers
import spec.support.Spec

class Classes extends Spec
{
	describe("Classes")
	{
		describe("empty, simple class")
		{
			it("sucessfully converts to D")
			{
	      "class Foo {}" shouldPortTo "class Foo {}"
			}
		}

		describe("with superclass")
		{
			it("sucessfully converts to D")
			{
				"class Foo {}" shouldPortTo "class Bar {}"
			}
		}
	}
}