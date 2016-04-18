package dwt.jport

import scala.collection.mutable.StringBuilder

import dwt.jport.core.JPortAny.anyToJPortAny
import dwt.jport.util.ThreadLocalVariable

object DCoder {
  object nl {
    override def toString = "\n"
  }

  private var _dcoder = new ThreadLocalVariable(new DCoder)
  def dcoder = _dcoder.get
}

class DCoder {
  import DCoder.nl

  private var buffer: StringBuilder = _
  private var level: Int = _
  private var indent_? : Boolean = _

  reset()

  def result = {
    stripNewlines()
    buffer.result
  }

  override def toString = result

  def +=[T](t: T): Unit = append(t)
  def +=(c: nl.type): Unit = append(nl)
  def +=:[T](t: T): Unit = prepend(t)

  def :+[T](t: T): Unit = append(t)
  def :+(c: nl.type): Unit = append(nl)

  def append[T](args: T*) = this.tap {
    args.foreach(doAppend(_))
  }

  private def doAppend[T](arg: T) = {
    arg match {
      case DCoder.nl => {
        buffer.append(nl)
        indent_? = true
      }
      case _ => {
        doIndent()
        buffer.append(arg)
        indent_? = false
      }
    }
  }

  def prepend[T](args: T*) = this.tap {
    doIndent()
    args.foreach(buffer.insert(0, _))
    indent_? = false
  }

  def indent(block: => Unit) = this.tap {
    level += 1
    block
    level -= 1
  }

  def unindent(block: => Unit) = this.tap {
    level -= 1
    block
    level += 1
  }

  def increaseIndentation: DCoder = {
    increaseIndentation(1)
    this
  }

  def decreaseIndentation: DCoder = {
    decreaseIndentation(1)
    this
  }

  def increaseIndentation(level: Int): DCoder = this.tap(this.level += level)
  def decreaseIndentation(level: Int): DCoder = this.tap(this.level -= level)

  def join(iterable: Iterable[_ <: String], separator: String = ", ") = this.tap {
    var first = true

    for (e <- iterable) {
      if (first) {
        append(e)
        first = false
      }
      else {
        append(separator)
        append(e)
      }
    }
  }

  def stripNewlines(): DCoder = {
    val start = (buffer.length - 1).to(0).by(-1).find(buffer.charAt(_) != '\n').
      getOrElse(buffer.length)

    if (start < buffer.length)
      buffer.delete(start + 1, buffer.length)

    this
  }

  def reset() = {
    buffer = new StringBuilder
    level = 0
    indent_? = false
    this
  }

  private def doIndent() = if (indent_?) buffer.append(" " * 4 * level)
}
