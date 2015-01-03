package dwt.jport.writers

import dwt.jport.DCoder
import dwt.jport.util.ThreadLocalVariable

trait Buffer {
  protected val nl = DCoder.nl
  protected val buffer = DCoder.dcoder
}

trait Writer[T] extends Buffer {
  protected var node: T = _
  protected var importWriter: ImportWriter = null

  def write(importWriter: ImportWriter, node: T): Unit
  def postWrite
}

abstract class WriterObject[Node, Subclass <: Writer[Node]](implicit manifest: Manifest[Subclass]) {
  private def newInstance = manifest.runtimeClass.newInstance.asInstanceOf[Subclass]
  private val _writer = new ThreadLocalVariable(newInstance)
  private def writer = _writer.get

  def write(importWriter: ImportWriter, node: Node) = writer.write(importWriter, node)
  def postWrite: Unit = writer.postWrite
}
