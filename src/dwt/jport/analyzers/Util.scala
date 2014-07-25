package dwt.jport.analyzers

import javax.lang.model.element.Modifier
import scala.collection.GenTraversableOnce
import scala.collection.mutable.ArrayBuffer

object Util
{
  private object Protection extends Enumeration
  {
    type Protection = Value

    val PACKAGE = Value("package")
    val PUBLIC = Value("public")
    val PROTECTED = Value("protected")
    val PRIVATE = Value("private")

    def fromModifier (value: Modifier) = value match {
      case Modifier.PUBLIC => PUBLIC
      case Modifier.PROTECTED => PROTECTED
      case Modifier.PRIVATE => PRIVATE
      case _ => PACKAGE
    }
  }

  private object Attribute extends Enumeration
  {
    type Attribute = Value

    val ABSTRACT = Value("abstract")
    val STATIC = Value("static")
    val FINAL = Value("final")
    val NONE = Value("")

    def fromModifier (value: Modifier) = value match {
      case Modifier.ABSTRACT => ABSTRACT
      case Modifier.STATIC => STATIC
      case Modifier.FINAL => FINAL
      case _ => NONE
    }
  }

  def modifiersToString (modifiers: GenTraversableOnce[javax.lang.model.element.Modifier]): String = {
    val protection = modifiers.find(e => Protection.values.contains(Protection.apply(e.ordinal))).
      getOrElse(Protection.PACKAGE)

    var attribute = Attribute.NONE

    for (e <- modifiers)
    {
      val en = Attribute.apply(e.ordinal)

      if (Attribute.values.contains(en))
        attribute = en
    }

    var result = protection.toString

	if (attribute != Attribute.NONE)
	  result += " " + attribute.toString

	result
  }
}
