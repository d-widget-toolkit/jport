package dwt.jport.util

class ThreadLocalVariable[T](initValue: => T) extends java.lang.ThreadLocal[T] {
  override def initialValue: T = initValue
}