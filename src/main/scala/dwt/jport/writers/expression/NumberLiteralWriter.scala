package dwt.jport.writers.expression

import dwt.jport.ast.expressions.NumberLiteral
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object NumberLiteralWriter extends WriterObject[NumberLiteral, NumberLiteralWriter]

class NumberLiteralWriter extends Writer[NumberLiteral] {
  override def write() = {
    buffer += node.translate
    this
  }
}
