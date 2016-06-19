package dwt.jport.analyzers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }

import dwt.jport.JPorter
import dwt.jport.ast.AstNode
import dwt.jport.ast.BodyDeclaration
import dwt.jport.ast.FieldDeclaration
import dwt.jport.ast.MethodDeclaration
import dwt.jport.ast.TypeDeclaration

import dwt.jport.ast.declarations.Initializer

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
import dwt.jport.ast.statements.SwitchCase
import dwt.jport.ast.statements.SwitchStatement
import dwt.jport.ast.statements.SynchronizedStatement
import dwt.jport.ast.statements.ThrowStatement
import dwt.jport.ast.statements.TryStatement
import dwt.jport.ast.statements.TypeDeclarationStatement
import dwt.jport.ast.statements.VariableDeclarationStatement
import dwt.jport.ast.statements.WhileStatement

import dwt.jport.writers.FieldDeclarationWriter
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.InitializerWriter
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
import dwt.jport.writers.statements.SwitchStatementWriter
import dwt.jport.writers.statements.SwitchCaseWriter
import dwt.jport.writers.statements.SynchronizedStatementWriter
import dwt.jport.writers.statements.ThrowStatementWriter
import dwt.jport.writers.statements.TryStatementWriter
import dwt.jport.writers.statements.CatchClauseWriter
import dwt.jport.writers.statements.WhileStatementWriter

class VisitData(val isFirst: Boolean, val next: Option[AstNode[ASTNode]],
  val prev: Option[AstNode[ASTNode]])

class JPortAstVisitor(private val importWriter: ImportWriter) {
  private type JavaList[T] = java.util.List[T]

  def visit(node: AstNode[ASTNode]): Unit = {
    node match {
      case n: TypeDeclaration => visit(n)
      case _ => JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
    }
  }

  def visit(node: TypeDeclaration): Unit = {
    val nodes = node.bodyDeclarations

    val writer = TypeDeclarationWriter(importWriter, node).write()
    val jportNodes = JPortConverter.convert(nodes)

    for (node <- jportNodes) {
      node match {
        case n: MethodDeclaration => visit(n)
        case n: FieldDeclaration => visit(n)
        case n: Initializer => visit(n)
        case _ => JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
      }
    }

    writer.postWrite
  }

  def visit(node: MethodDeclaration): Unit = {
    val writer = MethodDeclarationWriter(importWriter, node).write()
    JPortConverter.convert(node.statements).map(_.asInstanceOf[Statement])
      .foreach(visit)
    writer.postWrite
  }

  def visit(node: FieldDeclaration): Unit =
    FieldDeclarationWriter(importWriter, node).write().postWrite()

  def visit(node: Initializer): Unit = {
    val writer = InitializerWriter(importWriter, node).write()
    visit(node.body)
    writer.postWrite
  }

  def visit(node: VariableDeclarationStatement): Unit =
    VariableDeclarationStatementWriter(importWriter, node).write().postWrite()

  def visit(node: ExpressionStatement): Unit =
    ExpressionStatementWriter(importWriter, node).write().postWrite()

  def visit(node: ForStatement): Unit = {
    val writer = ForStatementWriter(importWriter, node).write()
    visit(node.body)
    writer.postWrite
  }

  def visit(node: Block, elseStatement: Boolean): Unit = {
    val writer = BlockWriter(importWriter, node, elseStatement).write()
    node.statements.foreach(visit)
    writer.postWrite
  }

  def visit(node: Statement): Unit = {
    node match {
      case n: VariableDeclarationStatement => visit(n)
      case n: ReturnStatement => visit(n)
      case n: ExpressionStatement => visit(n)
      case n: Block => visit(n, false)
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
      case n: TypeDeclarationStatement => visit(n)
      case n: WhileStatement => visit(n)
      case _ => JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
    }
  }

  def visit(node: EmptyStatement): Unit =
    EmptyStatementWriter(importWriter, node).write().postWrite()

  def visit(node: LabeledStatement): Unit = {
    val writer = LabeledStatementWriter(importWriter, node).write()
    visit(JPortConverter.convert(node.body, node.visitData))
    writer.postWrite
  }

  def visit(node: ControlFlowStatement): Unit =
    ControlFlowStatementWriter(importWriter, node).write().postWrite()

  def visit(node: ConstructorInvocation): Unit =
    ConstructorInvocationWriter(importWriter, node).write().postWrite()

  def visit(node: SuperConstructorInvocation): Unit =
    SuperConstructorInvocationWriter(importWriter, node).write().postWrite()

  def visit(node: DoStatement): Unit = {
    val writer = DoStatementWriter(importWriter, node).write()
    visit(JPortConverter.convert(node.body, node.visitData))
    writer.postWrite
  }

  def visit(node: IfStatement): Unit = {
    val writer = IfStatementWriter(importWriter, node).write()
    visit(node.thenStatement, false)
    writer.postWrite

    writer.writeElse
    node.elseStatement.map(visit(_, true))
  }

  def visit(node: ReturnStatement): Unit =
    ReturnStatementWriter(importWriter, node).write().postWrite()

  def visit(node: SwitchStatement): Unit = {
    val writer = SwitchStatementWriter(importWriter, node).write()
    JPortConverter.convert(node.statements).map(_.asInstanceOf[Statement])
      .foreach(visit)
    writer.postWrite
  }

  def visit(node: SwitchCase): Unit =
    SwitchCaseWriter(importWriter, node).write().postWrite()

  def visit(node: SynchronizedStatement): Unit = {
    val writer = SynchronizedStatementWriter(importWriter, node).write()
    visit(node.body)
    writer.postWrite
  }

  def visit(node: ThrowStatement): Unit =
    ThrowStatementWriter(importWriter, node).write().postWrite()

  def visit(node: TryStatement): Unit = {
    val writer = TryStatementWriter(importWriter, node).write()
    visit(node.body)
    node.catchClauses.foreach(visit)

    writer.writeFinally
    node.`finally`.map(visit)
    writer.postWrite
  }

  def visit(node: CatchClause): Unit = {
    val writer = CatchClauseWriter(importWriter, node).write()
    visit(node.body)
    writer.postWrite
  }

  def visit(node: TypeDeclarationStatement): Unit = {
    visit(node.declaration)
  }

  def visit(node: WhileStatement): Unit = {
    val writer = WhileStatementWriter(importWriter, node).write()
    visit(JPortConverter.convert(node.body, node.visitData))
    writer.postWrite
  }
}
