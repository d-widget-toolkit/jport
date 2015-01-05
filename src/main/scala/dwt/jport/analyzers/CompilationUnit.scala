package dwt.jport.analyzers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration
import org.eclipse.jdt.core.dom.{ CompilationUnit => JdtCompilationUnit }
import org.eclipse.jdt.core.dom.TypeDeclaration

import dwt.jport.DCoder
import dwt.jport.writers.ImportWriter

class CompilationUnit(val node: JdtCompilationUnit) extends Visitor {
  private type NodeType = AbstractTypeDeclaration
  private type JavaList[T] = java.util.List[T]

  private val importWriter = new ImportWriter
  private val visitor = new JPortAstVisitor(importWriter)
  private val nodes = node.types.asInstanceOf[JavaList[NodeType]].to[Array]

  def dcoder = DCoder.dcoder

  def process(): String = {
    dcoder.reset()

    accept(nodes) { (node, visitData) =>
      node match {
        case n: TypeDeclaration => visitor.visit(n, visitData)
        case _ => println(s"unhandled node $node")
      }
    }

    importWriter.write()
    dcoder.result
  }
}
