package dwt.jport.analyzers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ Block => JdtBlock }
import org.eclipse.jdt.core.dom.{ BreakStatement => JdtBreakStatement }
import org.eclipse.jdt.core.dom.{ ConstructorInvocation => JdtConstructorInvocation }
import org.eclipse.jdt.core.dom.{ EmptyStatement => JdtEmptyStatement }
import org.eclipse.jdt.core.dom.{ ExpressionStatement => JdtExpressionStatement }
import org.eclipse.jdt.core.dom.{ ForStatement => JdtForStatement }
import org.eclipse.jdt.core.dom.{ LabeledStatement => JdtLabeledStatement }
import org.eclipse.jdt.core.dom.ReturnStatement
import org.eclipse.jdt.core.dom.Statement
import org.eclipse.jdt.core.dom.{ SuperConstructorInvocation => JdtSuperConstructorInvocation }
import org.eclipse.jdt.core.dom.{ VariableDeclarationStatement => JdtVariableDeclarationStatement }

import dwt.jport.JPorter
import dwt.jport.ast.FieldDeclaration
import dwt.jport.ast.MethodDeclaration
import dwt.jport.ast.TypeDeclaration
import dwt.jport.ast.statements.Block
import dwt.jport.ast.statements.BreakStatement
import dwt.jport.ast.statements.ConstructorInvocation
import dwt.jport.ast.statements.EmptyStatement
import dwt.jport.ast.statements.ExpressionStatement
import dwt.jport.ast.statements.ForStatement
import dwt.jport.ast.statements.LabeledStatement
import dwt.jport.ast.statements.SuperConstructorInvocation
import dwt.jport.ast.statements.VariableDeclarationStatement
import dwt.jport.writers.FieldDeclarationWriter
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.MethodDeclarationWriter
import dwt.jport.writers.TypeDeclarationWriter
import dwt.jport.writers.statements.BlockWriter
import dwt.jport.writers.statements.BreakStatementWriter
import dwt.jport.writers.statements.ConstructorInvocationWriter
import dwt.jport.writers.statements.EmptyStatementWriter
import dwt.jport.writers.statements.ExpressionStatementWriter
import dwt.jport.writers.statements.ForStatementWriter
import dwt.jport.writers.statements.LabeledStatementWriter
import dwt.jport.writers.statements.SuperConstructorInvocationWriter
import dwt.jport.writers.statements.VariableDeclarationStatementWriter

class VisitData[T](val isFirst: Boolean, val next: Option[T],
  val prev: Option[T])

class JPortAstVisitor(private val importWriter: ImportWriter) extends Visitor {
  private type JavaList[T] = java.util.List[T]

  def visit(node: TypeDeclaration): Unit = {
    val nodes = node.bodyDeclarations

    TypeDeclarationWriter.write(importWriter, node)

    for (node <- JPortConverter.convert(nodes)) {
      node match {
        case n: MethodDeclaration => visit(n)
        case n: FieldDeclaration => visit(n)
        case _ => JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
      }
    }

    TypeDeclarationWriter.postWrite
  }

  def visit(node: MethodDeclaration): Unit = {
    MethodDeclarationWriter.write(importWriter, node)
    acceptStatements(node.statements.to[Array])
    MethodDeclarationWriter.postWrite
  }

  def visit(node: FieldDeclaration): Unit = {
    FieldDeclarationWriter.write(importWriter, node)
    FieldDeclarationWriter.postWrite
  }

  def visit(node: JdtVariableDeclarationStatement, visitData: VisitData[Statement]): Unit = {
    val jportNode = new VariableDeclarationStatement(node, visitData)
    VariableDeclarationStatementWriter.write(importWriter, jportNode)
    VariableDeclarationStatementWriter.postWrite
  }

  def visit(node: JdtExpressionStatement, visitData: VisitData[Statement]): Unit = {
    val jportNode = new ExpressionStatement(node, visitData)
    ExpressionStatementWriter.write(importWriter, jportNode)
    ExpressionStatementWriter.postWrite
  }

  def visit(node: JdtForStatement, visitData: VisitData[Statement]): Unit = {
    val jportNode = new ForStatement(node, visitData)

    ForStatementWriter.write(importWriter, jportNode)
    visit(jportNode.body, visitData)
    ForStatementWriter.postWrite
  }

  def visit(node: JdtBlock, visitData: VisitData[Statement]): Unit = {
    val jportNode = new Block(node, visitData)

    BlockWriter.write(importWriter, jportNode)
    acceptStatements(jportNode.statements)
    BlockWriter.postWrite
  }

  def visit(node: Statement, visitData: VisitData[Statement]): Unit = {
    node match {
      case n: JdtVariableDeclarationStatement => visit(n, visitData)
      case n: ReturnStatement => /* ignore during development */
      case n: JdtExpressionStatement => visit(n, visitData)
      case n: JdtBlock => visit(n, visitData)
      case n: JdtEmptyStatement => visit(n, visitData)
      case n: JdtForStatement => visit(n, visitData)
      case n: JdtLabeledStatement => visit(n, visitData)
      case n: JdtBreakStatement => visit(n, visitData)
      case n: JdtConstructorInvocation => visit(n, visitData)
      case n: JdtSuperConstructorInvocation => visit(n, visitData)
      case _ => JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
    }
  }

  def visit(node: JdtEmptyStatement, visitData: VisitData[Statement]): Unit = {
    val jportNode = new EmptyStatement(node, visitData)

    EmptyStatementWriter.write(importWriter, jportNode)
    EmptyStatementWriter.postWrite
  }

  def visit(node: JdtLabeledStatement, visitData: VisitData[Statement]): Unit = {
    val jportNode = new LabeledStatement(node, visitData)

    LabeledStatementWriter.write(importWriter, jportNode)
    visit(jportNode.body, visitData)
    LabeledStatementWriter.postWrite
  }

  def visit(node: JdtBreakStatement, visitData: VisitData[Statement]): Unit = {
    val jportNode = new BreakStatement(node, visitData)

    BreakStatementWriter.write(importWriter, jportNode)
    BreakStatementWriter.postWrite
  }

  def visit(node: JdtConstructorInvocation, visitData: VisitData[Statement]): Unit = {
    val jportNode = new ConstructorInvocation(node, visitData)

    ConstructorInvocationWriter.write(importWriter, jportNode)
    ConstructorInvocationWriter.postWrite
  }

  def visit(node: JdtSuperConstructorInvocation, visitData: VisitData[Statement]): Unit = {
    val jportNode = new SuperConstructorInvocation(node, visitData)

    SuperConstructorInvocationWriter.write(importWriter, jportNode)
    SuperConstructorInvocationWriter.postWrite
  }

  def acceptStatements(statements: Array[Statement]) =
    accept(statements) { (node, visitData) => visit(node, visitData) }
}
