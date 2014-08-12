package dwt.jport.core

class JPortAny (val obj: String)
{
  type T = String
  def tap (block: T => Unit): T = {
    block.apply(obj)
    obj
  }
}

object JPortAny
{
  type T = String
  implicit def anyToJPortAny (obj: T) = new JPortAny(obj)
}