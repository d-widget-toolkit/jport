package dwt.jport.core

class JPortAny[T](val obj: T) {
  def tap(block: T => Unit): T = {
    block.apply(obj)
    obj
  }

  def tap(block: => Unit): T = {
    block
    obj
  }
}

object JPortAny {
  implicit def anyToJPortAny[T](obj: T) = new JPortAny(obj)
}
