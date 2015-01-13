package dwt.jport.analyzers

import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration
import org.eclipse.jdt.core.dom.{ CompilationUnit => JdtCompilationUnit }
import org.eclipse.jdt.core.dom.TypeDeclaration
import dwt.jport.DCoder
import dwt.jport.writers.ImportWriter
import org.eclipse.jdt.core.dom.ASTNode

class CompilationUnit(val unit: JdtCompilationUnit) extends Visitor {
  private type NodeType = AbstractTypeDeclaration
  private type JavaList[T] = java.util.List[T]

  private val importWriter = new ImportWriter
  private val visitor = new JPortAstVisitor(importWriter)
  private val nodes = unit.types.asInstanceOf[JavaList[NodeType]].to[Array]

  def dcoder = DCoder.dcoder

  def process(): String = {
    dcoder.reset()

    accept(nodes) { (node, visitData) =>
      node match {
        case n: TypeDeclaration => visitor.visit(n, visitData)
        case _ => println(s"unhandled node ${node.getClass.getName}")
      }
    }

    importWriter.write()
    dcoder.result
  }

  def getLineNumber(node: ASTNode) = unit.getLineNumber(node.getStartPosition)
}
