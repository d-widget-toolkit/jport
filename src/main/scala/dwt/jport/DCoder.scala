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

  private var buffer = new StringBuilder
  private var level = 0
  private var indent_? = false

  def result = {
    stripNewlines()
    buffer.result
  }

  override def toString = result

  def +=[T](t: T): Unit = append(t)
  def +=(c: nl.type): Unit = appendNewline
  def +=:[T](t: T): Unit = prepend(t)

  def :+[T](t: T): Unit = append(t)
  def :+(c: nl.type): Unit = appendNewline

  private def appendNewline = this.tap {
    append(nl)
    indent_? = true
  }

  def append[T](args: T*) = this.tap {
    doIndent()
    args.foreach(buffer.append(_))
    indent_? = false
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

  def increaseIndentation = this.tap(level += 1)
  def decreaseIndentation = this.tap(level -= 1)

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

  def reset() = this.tap(buffer = new StringBuilder)

  private def doIndent() = if (indent_?) buffer.append(" " * 4 * level)
}
