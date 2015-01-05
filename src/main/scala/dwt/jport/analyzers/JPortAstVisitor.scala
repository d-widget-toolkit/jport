package dwt.jport.analyzers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration
import org.eclipse.jdt.core.dom.BodyDeclaration
import org.eclipse.jdt.core.dom.{ MethodDeclaration => JdtMethodDeclaration }
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }

import dwt.jport.ast.MethodDeclaration
import dwt.jport.ast.TypeDeclaration
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.MethodDeclarationWriter
import dwt.jport.writers.TypeDeclarationWriter

class VisitData[T](val isFirst: Boolean, val next: Option[T],
  val prev: Option[T])

class JPortAstVisitor(private val importWriter: ImportWriter) extends Visitor {
  private type JavaList[T] = java.util.List[T]

  def visit(node: JdtTypeDeclaration, visistData: VisitData[AbstractTypeDeclaration]): Unit = {
    val jportNode = new TypeDeclaration(node, visistData)
    val nodes = node.bodyDeclarations.asInstanceOf[JavaList[BodyDeclaration]]

    TypeDeclarationWriter.write(importWriter, jportNode)

    accept(nodes.to[Array]) { (node, v) =>
      node match {
        case n: JdtMethodDeclaration => visit(n, v)
        case _ => println(s"unhandled node $node")
      }
    }

    TypeDeclarationWriter.postWrite
  }

  def visit(node: JdtMethodDeclaration, visitData: VisitData[BodyDeclaration]): Unit = {
    val jportNode = new MethodDeclaration(node, visitData)
    MethodDeclarationWriter.write(importWriter, jportNode)
    MethodDeclarationWriter.postWrite
  }
}
