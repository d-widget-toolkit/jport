package dwt.jport.writers.statements

import dwt.jport.ast.statements.SuperConstructorInvocation
import dwt.jport.translators.InvocationTranslator
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.NewlineWriter

object SuperConstructorInvocationWriter extends WriterObject[SuperConstructorInvocation, SuperConstructorInvocationWriter]

class SuperConstructorInvocationWriter
  extends Writer[SuperConstructorInvocation]
  with InvocationTranslator
  with NewlineWriter[SuperConstructorInvocation] {

  override def write(importWriter: ImportWriter, node: SuperConstructorInvocation): Unit = {
    super.write(importWriter, node)
    buffer += translate(node)

    importWriter += node.imports
  }

  override def postWrite: Unit = {
    buffer.append(';')
    super[NewlineWriter].postWrite
  }
}
