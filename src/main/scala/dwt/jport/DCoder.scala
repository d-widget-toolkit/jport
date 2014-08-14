package dwt.jport

import scala.collection.mutable.StringBuilder
import scala.util.DynamicVariable

object DCoder
{
  object nl
  {
    override def toString = "\n"
  }

  private var _dcoder = new DynamicVariable[DCoder](new DCoder)
  def dcoder = _dcoder.value
}

class DCoder
{
  import DCoder.nl

  private var buffer = new StringBuilder
  private var level = 0
  private var indent_? = false

  def result = {
    stripNewlines()
    buffer.result
  }

  override def toString = result

  def += [T] (t: T): Unit = append(t)
  def += (c: nl.type): Unit = append(nl)

  def append [T] (args: T*): Unit = {
    doIndent()
    args.foreach(buffer.append(_))
    indent_? = false
  }

  def indent (block: => Unit): Unit = {
    level += 1
    block
    level -= 1
  }

  def join (iterable: Iterable[_ <: String], separator: String): Unit = {
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

  def stripNewlines (): Unit = {
    val start = (buffer.length - 1).to(0).by(-1).find(buffer.charAt(_) != '\n').
    getOrElse(buffer.length)

    if (start < buffer.length)
      buffer.delete(start + 1, buffer.length)
  }

  def reset (): Unit = buffer = new StringBuilder

  private def doIndent () = if (indent_?) buffer.append("\t" * level)
}
