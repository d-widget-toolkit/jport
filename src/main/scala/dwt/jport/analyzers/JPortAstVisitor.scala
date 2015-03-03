package dwt.jport.analyzers

import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration
import org.eclipse.jdt.core.dom.BodyDeclaration
import org.eclipse.jdt.core.dom.{ FieldDeclaration => JdtFieldDeclaration }
import org.eclipse.jdt.core.dom.{ MethodDeclaration => JdtMethodDeclaration }
import org.eclipse.jdt.core.dom.Statement
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }
import org.eclipse.jdt.core.dom.{ VariableDeclarationStatement => JdtVariableDeclarationStatement }
import dwt.jport.JPorter
import dwt.jport.ast.FieldDeclaration
import dwt.jport.ast.MethodDeclaration
import dwt.jport.ast.TypeDeclaration
import dwt.jport.ast.statements.VariableDeclarationStatement
import dwt.jport.writers.FieldDeclarationWriter
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.MethodDeclarationWriter
import dwt.jport.writers.TypeDeclarationWriter
import dwt.jport.writers.statements.VariableDeclarationStatementWriter
import org.eclipse.jdt.core.dom.ReturnStatement

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
        case n: JdtFieldDeclaration => visit(n, v)
        case _ => println(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
      }
    }

    TypeDeclarationWriter.postWrite
  }

  def visit(node: JdtMethodDeclaration, visitData: VisitData[BodyDeclaration]): Unit = {
    val jportNode = new MethodDeclaration(node, visitData)

    MethodDeclarationWriter.write(importWriter, jportNode)

    accept(jportNode.statements.to[Array]) { (node, visitData) =>
      node match {
        case n: JdtVariableDeclarationStatement => visit(n, visitData)
        case n: ReturnStatement => /* ignore during development */
        case _ => JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
      }
    }

    MethodDeclarationWriter.postWrite
  }

  def visit(node: JdtFieldDeclaration, visitData: VisitData[BodyDeclaration]): Unit = {
    val jportNode = new FieldDeclaration(node, visitData)
    FieldDeclarationWriter.write(importWriter, jportNode)
    FieldDeclarationWriter.postWrite
  }

  def visit(node: JdtVariableDeclarationStatement, visitData: VisitData[Statement]): Unit = {
    val jportNode = new VariableDeclarationStatement(node, visitData)
    VariableDeclarationStatementWriter.write(importWriter, jportNode)
    VariableDeclarationStatementWriter.postWrite
  }
}
