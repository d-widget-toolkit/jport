package dwt.jport.writers

import dwt.jport.ast.declarations.Initializer

object InitializerWriter extends WriterObject[Initializer, InitializerWriter]

class InitializerWriter extends Writer[Initializer] {
  override def write(importWriter: ImportWriter, node: Initializer): Unit = {
    super.write(importWriter, node)

    buffer += "shared static this()"
  }
}
