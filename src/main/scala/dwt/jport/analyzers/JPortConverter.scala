package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ AbstractTypeDeclaration => JdtAbstractTypeDeclaration }
import org.eclipse.jdt.core.dom.{ Block => JdtBlock }
import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.{ BreakStatement => JdtBreakStatement }
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
import dwt.jport.ast.statements.Statement
import dwt.jport.ast.statements.SuperConstructorInvocation
import dwt.jport.ast.statements.VariableDeclarationStatement
import dwt.jport.ast.statements.SwitchStatement
import dwt.jport.ast.statements.SwitchCase
import dwt.jport.ast.statements.SynchronizedStatement
import dwt.jport.ast.statements.ThrowStatement

object JPortConverter {
  def convert[T <: ASTNode, U <: AstNode[_]](nodes: Iterable[T]): Iterator[U] = {
    window(nodes).zipWithIndex map {
      case ((prev, node, next), index) =>
        convert(node.get, index == 0, next, prev)
    }
  }

  def convert[T <: ASTNode, U <: AstNode[T]](node: T, isFirst: Boolean = false,
    next: Option[T] = None,
    prev: Option[T] = None): U = {

    def option[U](o: Option[T]) = o.asInstanceOf[Option[U]]

    node match {
      case n: JdtAbstractTypeDeclaration => {
        val p = option[JdtAbstractTypeDeclaration](prev)
        val ne = option[JdtAbstractTypeDeclaration](next)
        convert(n, isFirst, ne, p).asInstanceOf[U]
      }

      case n: JdtBodyDeclaration => {
        val ne = option[JdtBodyDeclaration](next)
        val p = option[JdtBodyDeclaration](prev)
        convert(n, isFirst, ne, p).asInstanceOf[U]
      }

      case n: JdtStatement => {
        val ne = option[JdtStatement](next)
        val p = option[JdtStatement](prev)
        convert(n, isFirst, ne, p).asInstanceOf[U]
      }

      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null.asInstanceOf[U]
      }
    }
  }

  private def convert(node: JdtAbstractTypeDeclaration, isFirst: Boolean,
    next: Option[JdtAbstractTypeDeclaration],
    prev: Option[JdtAbstractTypeDeclaration]): AbstractTypeDeclaration = {

    val ne = next.map(convert(_).asInstanceOf[AbstractTypeDeclaration])
    val p = prev.map(convert(_).asInstanceOf[AbstractTypeDeclaration])
    val visitData = new VisitData(isFirst, ne, p)

    node match {
      case n: JdtTypeDeclaration => new TypeDeclaration(n, visitData)
      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null
      }
    }
  }

  private def convert(node: JdtBodyDeclaration, isFirst: Boolean,
    next: Option[JdtBodyDeclaration],
    prev: Option[JdtBodyDeclaration]): BodyDeclaration = {

    val ne = next.map(convert(_).asInstanceOf[BodyDeclaration])
    val p = prev.map(convert(_).asInstanceOf[BodyDeclaration])
    val visitData = new VisitData(isFirst, ne, p)

    node match {
      case n: JdtMethodDeclaration => new MethodDeclaration(n, visitData)
      case n: JdtFieldDeclaration => new FieldDeclaration(n, visitData)
      case _ => {
        JPorter.diagnostic.unhandled(s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
        null
      }
    }
  }

  private def convert(node: JdtStatement, isFirst: Boolean,
    next: Option[JdtStatement],
    prev: Option[JdtStatement]): Statement = {

    val ne = next.map(convert(_).asInstanceOf[Statement])
    val p = prev.map(convert(_).asInstanceOf[Statement])
    val visitData = new VisitData(isFirst, ne, p)

    convert(node, visitData)
  }

  def convert(node: JdtStatement, visitData: VisitData[Statement]): Statement = {
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
