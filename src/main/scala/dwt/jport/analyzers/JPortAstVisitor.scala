package dwt.jport.analyzers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }

import dwt.jport.JPorter
import dwt.jport.ast.BodyDeclaration
import dwt.jport.ast.FieldDeclaration
import dwt.jport.ast.MethodDeclaration
import dwt.jport.ast.TypeDeclaration
import dwt.jport.ast.statements.Block
import dwt.jport.ast.statements.BreakStatement
import dwt.jport.ast.statements.CatchClause
import dwt.jport.ast.statements.ConstructorInvocation
import dwt.jport.ast.statements.ContinueStatement
import dwt.jport.ast.statements.ControlFlowStatement
import dwt.jport.ast.statements.DoStatement
import dwt.jport.ast.statements.EmptyStatement
import dwt.jport.ast.statements.ExpressionStatement
import dwt.jport.ast.statements.ForStatement
import dwt.jport.ast.statements.IfStatement
import dwt.jport.ast.statements.LabeledStatement
import dwt.jport.ast.statements.ReturnStatement
import dwt.jport.ast.statements.Statement
import dwt.jport.ast.statements.SuperConstructorInvocation
import dwt.jport.ast.statements.SynchronizedStatement
import dwt.jport.ast.statements.ThrowStatement
import dwt.jport.ast.statements.TryStatement
import dwt.jport.ast.statements.VariableDeclarationStatement

import dwt.jport.writers.FieldDeclarationWriter
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.MethodDeclarationWriter
import dwt.jport.writers.TypeDeclarationWriter
import dwt.jport.writers.statements.BlockWriter
import dwt.jport.writers.statements.ConstructorInvocationWriter
import dwt.jport.writers.statements.ControlFlowStatementWriter
import dwt.jport.writers.statements.DoStatementWriter
import dwt.jport.writers.statements.EmptyStatementWriter
import dwt.jport.writers.statements.ExpressionStatementWriter
import dwt.jport.writers.statements.ForStatementWriter
import dwt.jport.writers.statements.LabeledStatementWriter
import dwt.jport.writers.statements.SuperConstructorInvocationWriter
import dwt.jport.writers.statements.VariableDeclarationStatementWriter
import dwt.jport.writers.statements.IfStatementWriter
import dwt.jport.writers.statements.ReturnStatementWriter
import dwt.jport.ast.statements.SwitchStatement
import dwt.jport.writers.statements.SwitchStatementWriter
import dwt.jport.ast.statements.SwitchCase
import dwt.jport.writers.statements.SwitchCaseWriter
import dwt.jport.writers.statements.SynchronizedStatementWriter
import dwt.jport.writers.statements.ThrowStatementWriter
import dwt.jport.writers.statements.TryStatementWriter
import dwt.jport.writers.statements.CatchClauseWriter

class VisitData[T](val isFirst: Boolean, val next: Option[T],
  val prev: Option[T])

class JPortAstVisitor(private val importWriter: ImportWriter) extends Visitor {
  private type JavaList[T] = java.util.List[T]

  def visit(node: TypeDeclaration): Unit = {
    val nodes = node.bodyDeclarations

    TypeDeclarationWriter.write(importWriter, node)
    val jportNodes = JPortConverter.convert[JdtBodyDeclaration, BodyDeclaration](nodes)

    for (node <- jportNodes) {
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
    JPortConverter.convert[JdtStatement, Statement](node.statements).foreach(visit)
    MethodDeclarationWriter.postWrite
  }

  def visit(node: FieldDeclaration): Unit = {
    FieldDeclarationWriter.write(importWriter, node)
    FieldDeclarationWriter.postWrite
  }

  def visit(node: VariableDeclarationStatement): Unit = {
    VariableDeclarationStatementWriter.write(importWriter, node)
    VariableDeclarationStatementWriter.postWrite
  }

  def visit(node: ExpressionStatement): Unit = {
    ExpressionStatementWriter.write(importWriter, node)
    ExpressionStatementWriter.postWrite
  }

  def visit(node: ForStatement): Unit = {
    ForStatementWriter.write(importWriter, node)
    visit(JPortConverter.convert(node.body, node.visitData))
    ForStatementWriter.postWrite
  }

  def visit(node: Block): Unit = {
    BlockWriter.write(importWriter, node)
    JPortConverter.convert[JdtStatement, Statement](node.statements).foreach(visit)
    BlockWriter.postWrite
  }

  def visit(node: Statement): Unit = {
    node match {
      case n: VariableDeclarationStatement => visit(n)
      case n: ReturnStatement => visit(n)
      case n: ExpressionStatement => visit(n)
      case n: Block => visit(n)
      case n: EmptyStatement => visit(n)
      case n: ForStatement => visit(n)
      case n: LabeledStatement => visit(n)
      case n: BreakStatement => visit(n)
      case n: ConstructorInvocation => visit(n)
      case n: SuperConstructorInvocation => visit(n)
      case n: ContinueStatement => visit(n)
      case n: DoStatement => visit(n)
      case n: IfStatement => visit(n)
      case n: SwitchStatement => visit(n)
      case n: SwitchCase => visit(n)
      case n: SynchronizedStatement => visit(n)
      case n: ThrowStatement => visit(n)
      case n: TryStatement => visit(n)
      case _ => JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
    }
  }

  def visit(node: EmptyStatement): Unit = {
    EmptyStatementWriter.write(importWriter, node)
    EmptyStatementWriter.postWrite
  }

  def visit(node: LabeledStatement): Unit = {
    LabeledStatementWriter.write(importWriter, node)
    visit(JPortConverter.convert(node.body, node.visitData))
    LabeledStatementWriter.postWrite
  }

  def visit(node: ControlFlowStatement): Unit = {
    ControlFlowStatementWriter.write(importWriter, node)
    ControlFlowStatementWriter.postWrite
  }

  def visit(node: ConstructorInvocation): Unit = {
    ConstructorInvocationWriter.write(importWriter, node)
    ConstructorInvocationWriter.postWrite
  }

  def visit(node: SuperConstructorInvocation): Unit = {
    SuperConstructorInvocationWriter.write(importWriter, node)
    SuperConstructorInvocationWriter.postWrite
  }

  def visit(node: DoStatement): Unit = {
    DoStatementWriter.write(importWriter, node)
    visit(JPortConverter.convert(node.body, node.visitData))
    DoStatementWriter.postWrite
  }

  def visit(node: IfStatement): Unit = {
    val elseStatement = node.elseStatement.map(
      JPortConverter.convert(_, node.visitData))

    IfStatementWriter.write(importWriter, node)
    val next = if (elseStatement.isDefined) elseStatement else node.next
    val thenVisit = new VisitData[Statement](node.visitData.isFirst,
      next, node.visitData.prev)
    visit(JPortConverter.convert(node.thenStatement, thenVisit))

    IfStatementWriter.writeElse
    elseStatement.map(visit)
    IfStatementWriter.postWrite
  }

  def visit(node: ReturnStatement): Unit = {
    ReturnStatementWriter.write(importWriter, node)
    ReturnStatementWriter.postWrite
  }

  def visit(node: SwitchStatement): Unit = {
    SwitchStatementWriter.write(importWriter, node)
    JPortConverter.convert[JdtStatement, Statement](node.statements).foreach(visit)
    SwitchStatementWriter.postWrite
  }

  def visit(node: SwitchCase): Unit = {
    SwitchCaseWriter.write(importWriter, node)
    SwitchCaseWriter.postWrite
  }

  def visit(node: SynchronizedStatement): Unit = {
    SynchronizedStatementWriter.write(importWriter, node)
    visit(JPortConverter.convert(node.body, node.visitData))
    SynchronizedStatementWriter.postWrite
  }

  def visit(node: ThrowStatement): Unit = {
    ThrowStatementWriter.write(importWriter, node)
    ThrowStatementWriter.postWrite
  }

  def visit(node: TryStatement): Unit = {
    TryStatementWriter.write(importWriter, node)
    visit(node.body)
    node.catchClauses.foreach(visit)

    TryStatementWriter.writeFinally
    node.`finally`.map(visit)
    TryStatementWriter.postWrite
  }

  def visit(node: CatchClause): Unit = {
    CatchClauseWriter.write(importWriter, node)
    visit(node.body)
    CatchClauseWriter.postWrite
  }
}
