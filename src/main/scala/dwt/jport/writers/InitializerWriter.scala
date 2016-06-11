package dwt.jport.writers

import dwt.jport.ast.declarations.Initializer

object InitializerWriter extends WriterObject[Initializer, InitializerWriter]

class InitializerWriter extends Writer[Initializer] {
  override def write() = {
    buffer += "shared static this()"
    this
  }
}
