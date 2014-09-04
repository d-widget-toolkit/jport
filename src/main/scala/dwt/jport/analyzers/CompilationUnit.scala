package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.{ CompilationUnit => JdtCompilationUnit }
import dwt.jport.writers.ImportWriter
import dwt.jport.DCoder

class CompilationUnit(val unit: JdtCompilationUnit) {
  val importWriter = new ImportWriter
  def dcoder = DCoder.dcoder

  def process(): String = {
    dcoder.reset()
    unit.accept(new JPortAstVisitor(importWriter))
    importWriter.write()
    dcoder.result
  }
}