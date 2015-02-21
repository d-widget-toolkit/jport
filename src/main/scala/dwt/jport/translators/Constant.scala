package dwt.jport.translators

import dwt.jport.JPorter

object Constant {
  def translate(value: Object): String = {
    if (value == null) return ""

    val result: Option[String] = value match {
      case v: String => Some(s""""$v"""")
      case v: Integer => Some(v.toString())
      case v: java.lang.Double => Some(v.toString())
      case v: java.lang.Float => Some(v.toString() + 'f')
      case v: java.lang.Boolean => Some(v.toString())
      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled type $value")
        None
      }
    }

    result.getOrElse("")
  }
}
