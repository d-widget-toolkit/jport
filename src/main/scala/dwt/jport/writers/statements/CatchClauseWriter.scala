package dwt.jport.writers.statements

import dwt.jport.Type
import dwt.jport.Symbol

import dwt.jport.ast.statements.CatchClause
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.NewlineWriter

object CatchClauseWriter extends WriterObject[CatchClause, CatchClauseWriter]

class CatchClauseWriter extends Writer[CatchClause] {
  override def write() = {
    if (node.visitData.isFirst)
      buffer += nl

    buffer.append("catch (")
    writeException
    buffer += ')'

    importWriter += node.imports
    this
  }

  private def writeException = {
    val typ = Type.translate(node.exceptionType)
    val name = Symbol.translate(node.exceptionName)
    buffer.append(typ, ' ', name)
  }
}
