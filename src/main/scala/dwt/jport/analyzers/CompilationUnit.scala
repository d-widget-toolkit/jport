package dwt.jport.analyzers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration
import org.eclipse.jdt.core.dom.{ CompilationUnit => JdtCompilationUnit }

import dwt.jport.DCoder
import dwt.jport.JPorter
import dwt.jport.ast.TypeDeclaration
import dwt.jport.writers.ImportWriter

class CompilationUnit(val unit: JdtCompilationUnit) extends Visitor {
  private type NodeType = AbstractTypeDeclaration
  private type JavaList[T] = java.util.List[T]

  private val importWriter = new ImportWriter
  private val visitor = new JPortAstVisitor(importWriter)
  private val nodes = unit.types.asInstanceOf[JavaList[NodeType]].to[Array]

  def dcoder = DCoder.dcoder

  def process(): String = {
    dcoder.reset()

    for (node <- JPortConverter.convert(nodes)) {
      node match {
        case n: TypeDeclaration => visitor.visit(n)
        case _ => JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
      }
    }

    importWriter.write()
    dcoder.result
  }

  def getLineNumber(node: ASTNode) = unit.getLineNumber(node.getStartPosition)
}
