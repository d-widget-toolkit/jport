package dwt.jport.writers.statements

import dwt.jport.ast.statements.ConstructorInvocation
import dwt.jport.translators.InvocationTranslator
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object ConstructorInvocationWriter extends WriterObject[ConstructorInvocation, ConstructorInvocationWriter]

class ConstructorInvocationWriter
  extends Writer[ConstructorInvocation]
  with InvocationTranslator {

  override def write(importWriter: ImportWriter, node: ConstructorInvocation): Unit = {
    super.write(importWriter, node)
    buffer += translate(node)

    importWriter += node.imports
  }

  override def postWrite: Unit = {
    buffer.append(';', nl)
  }
}
