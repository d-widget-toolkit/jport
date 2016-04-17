package dwt.jport.analyzers

import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ AbstractTypeDeclaration => JdtAbstractTypeDeclaration }
import org.eclipse.jdt.core.dom.{ CompilationUnit => JdtCompilationUnit }
import dwt.jport.DCoder
import dwt.jport.JPorter
import dwt.jport.ast.TypeDeclaration
import dwt.jport.writers.ImportWriter
import dwt.jport.ast.AbstractTypeDeclaration
import dwt.jport.ast.AstNode

class CompilationUnit(val unit: JdtCompilationUnit) {
  private type NodeType = JdtAbstractTypeDeclaration
  private type JavaList[T] = java.util.List[T]

  private val importWriter = new ImportWriter
  private val visitor = new JPortAstVisitor(importWriter)
  private val nodes = unit.types.asInstanceOf[JavaList[NodeType]].to[Array]

  def dcoder = DCoder.dcoder

  def process(): String = {
    dcoder.reset()
    JPortConverter.convert(nodes).foreach(visitor.visit(_))

    importWriter.write()
    val r = dcoder.result
    println(r)
    r
  }

  def getLineNumber(node: AstNode[_]) = unit.getLineNumber(node.startPosition)
}
