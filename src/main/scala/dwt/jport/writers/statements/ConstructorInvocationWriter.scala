package dwt.jport.writers.statements

import dwt.jport.ast.statements.ConstructorInvocation
import dwt.jport.translators.InvocationTranslator
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.NewlineWriter

object ConstructorInvocationWriter extends WriterObject[ConstructorInvocation, ConstructorInvocationWriter]

class ConstructorInvocationWriter
  extends Writer[ConstructorInvocation]
  with InvocationTranslator
  with NewlineWriter[ConstructorInvocation] {

  override def write() = {
    buffer += translate(node)

    importWriter += node.imports
    this
  }

  override def postWrite: Unit = {
    buffer.append(';')
    super[NewlineWriter].postWrite()
  }
}
