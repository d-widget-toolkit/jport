package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ AbstractTypeDeclaration => JdtAbstractTypeDeclaration }
import org.eclipse.jdt.core.dom.{ Block => JdtBlock }
import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.{ BreakStatement => JdtBreakStatement }
import org.eclipse.jdt.core.dom.{ CatchClause => JdtCatchClause }
import org.eclipse.jdt.core.dom.{ CompilationUnit => JdtCompilationUnit }
import org.eclipse.jdt.core.dom.{ ConstructorInvocation => JdtConstructorInvocation }
import org.eclipse.jdt.core.dom.{ ContinueStatement => JdtContinueStatement }
import org.eclipse.jdt.core.dom.{ DoStatement => JdtDoStatement }
import org.eclipse.jdt.core.dom.{ EmptyStatement => JdtEmptyStatement }
import org.eclipse.jdt.core.dom.{ ExpressionStatement => JdtExpressionStatement }
import org.eclipse.jdt.core.dom.{ FieldDeclaration => JdtFieldDeclaration }
import org.eclipse.jdt.core.dom.{ ForStatement => JdtForStatement }
import org.eclipse.jdt.core.dom.{ IfStatement => JdtIfStatement }
import org.eclipse.jdt.core.dom.{ LabeledStatement => JdtLabeledStatement }
import org.eclipse.jdt.core.dom.{ MethodDeclaration => JdtMethodDeclaration }
import org.eclipse.jdt.core.dom.{ ReturnStatement => JdtReturnStatement }
import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }
import org.eclipse.jdt.core.dom.{ SwitchCase => JdtSwitchCase }
import org.eclipse.jdt.core.dom.{ SwitchStatement => JdtSwitchStatement }
import org.eclipse.jdt.core.dom.{ SuperConstructorInvocation => JdtSuperConstructorInvocation }
import org.eclipse.jdt.core.dom.{ SynchronizedStatement => JdtSynchronizedStatement }
import org.eclipse.jdt.core.dom.{ ThrowStatement => JdtThrowStatement }
import org.eclipse.jdt.core.dom.{ TryStatement => JdtTryStatement }
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }
import org.eclipse.jdt.core.dom.{ VariableDeclarationStatement => JdtVariableDeclarationStatement }

import dwt.jport.JPorter
import dwt.jport.ast.AbstractTypeDeclaration
import dwt.jport.ast.AstNode
import dwt.jport.ast.BodyDeclaration
import dwt.jport.ast.FieldDeclaration
import dwt.jport.ast.MethodDeclaration
import dwt.jport.ast.TypeDeclaration
import dwt.jport.ast.statements.Block
import dwt.jport.ast.statements.BreakStatement
import dwt.jport.ast.statements.ConstructorInvocation
import dwt.jport.ast.statements.ContinueStatement
import dwt.jport.ast.statements.DoStatement
import dwt.jport.ast.statements.EmptyStatement
import dwt.jport.ast.statements.ExpressionStatement
import dwt.jport.ast.statements.ForStatement
import dwt.jport.ast.statements.IfStatement
import dwt.jport.ast.statements.LabeledStatement
import dwt.jport.ast.statements.ReturnStatement
import dwt.jport.ast.statements.TypedStatement
import dwt.jport.ast.statements.SuperConstructorInvocation
import dwt.jport.ast.statements.VariableDeclarationStatement
import dwt.jport.ast.statements.SwitchStatement
import dwt.jport.ast.statements.SwitchCase
import dwt.jport.ast.statements.SynchronizedStatement
import dwt.jport.ast.statements.ThrowStatement
import dwt.jport.ast.statements.TryStatement
import dwt.jport.ast.statements.CatchClause
import dwt.jport.ast.statements.Statement

object JPortConverter {
  def convert[T <: ASTNode, U <: AstNode[_]](nodes: Iterable[T]): Iterator[AstNode[ASTNode]] = {
    window(nodes).zipWithIndex map {
      case ((prev, node, next), index) =>
        convert(node.get, index == 0, next, prev)
    }
  }

  def convert[T <: ASTNode, U <: AstNode[T]](node: T, isFirst: Boolean = false,
    next: Option[T] = None,
    prev: Option[T] = None): AstNode[ASTNode] =
    convert[T, U](node, convert(isFirst, next, prev))

  private def convert[T <: ASTNode](isFirst: Boolean,
    next: Option[T], prev: Option[T]): VisitData =
    new VisitData(isFirst, next.map(convert(_)), prev.map(convert(_)))

  def convert[T <: ASTNode, U <: AstNode[T]](node: T, visitData: VisitData): AstNode[ASTNode] = {
    node match {
      case n: JdtAbstractTypeDeclaration => convert(n, visitData)
      case n: JdtBodyDeclaration => convert(n, visitData)
      case n: JdtCatchClause => convert(n, visitData)
      case n: JdtStatement => convert(n, visitData)

      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null
      }
    }
  }

  private def convert(node: JdtAbstractTypeDeclaration,
    visitData: VisitData): AbstractTypeDeclaration = {

    node match {
      case n: JdtTypeDeclaration => new TypeDeclaration(n, visitData)
      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null
      }
    }
  }

  private def convert(node: JdtBodyDeclaration,
    visitData: VisitData): BodyDeclaration = {

    node match {
      case n: JdtMethodDeclaration => new MethodDeclaration(n, visitData)
      case n: JdtFieldDeclaration => new FieldDeclaration(n, visitData)
      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null
      }
    }
  }

  private def convert(node: JdtCatchClause,
    visitData: VisitData): CatchClause =
    new CatchClause(node, visitData)

  def convert(node: JdtStatement, visitData: VisitData): Statement = {
    node match {
      case n: JdtVariableDeclarationStatement => new VariableDeclarationStatement(n, visitData)
      case n: JdtReturnStatement => new ReturnStatement(n, visitData)
      case n: JdtExpressionStatement => new ExpressionStatement(n, visitData)
      case n: JdtBlock => new Block(n, visitData)
      case n: JdtEmptyStatement => new EmptyStatement(n, visitData)
      case n: JdtForStatement => new ForStatement(n, visitData)
      case n: JdtLabeledStatement => new LabeledStatement(n, visitData)
      case n: JdtBreakStatement => new BreakStatement(n, visitData)
      case n: JdtConstructorInvocation => new ConstructorInvocation(n, visitData)
      case n: JdtSuperConstructorInvocation => new SuperConstructorInvocation(n, visitData)
      case n: JdtContinueStatement => new ContinueStatement(n, visitData)
      case n: JdtDoStatement => new DoStatement(n, visitData)
      case n: JdtIfStatement => new IfStatement(n, visitData)
      case n: JdtSwitchStatement => new SwitchStatement(n, visitData)
      case n: JdtSwitchCase => new SwitchCase(n, visitData)
      case n: JdtSynchronizedStatement => new SynchronizedStatement(n, visitData)
      case n: JdtThrowStatement => new ThrowStatement(n, visitData)
      case n: JdtTryStatement => new TryStatement(n, visitData)
      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null
      }
    }
  }

  private def window[T](list: Iterable[T]) =
    if (list.isEmpty)
      new Array(0).iterator
    else
      (None +: list.map(Some(_)) ++: Array(None)).
        sliding(3).map(e => (e(0), e(1), e(2)))
}
