package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ ArrayAccess => JdtArrayAccess }
import org.eclipse.jdt.core.dom.{ ArrayCreation => JdtArrayCreation }
import org.eclipse.jdt.core.dom.{ ArrayInitializer => JdtArrayInitializer }
import org.eclipse.jdt.core.dom.{ Assignment => JdtAssignment }
import org.eclipse.jdt.core.dom.{ BooleanLiteral => JdtBooleanLiteral }
import org.eclipse.jdt.core.dom.{ CastExpression => JdtCastExpression }
import org.eclipse.jdt.core.dom.{ CharacterLiteral => JdtCharacterLiteral }
import org.eclipse.jdt.core.dom.{ ClassInstanceCreation => JdtClassInstanceCreation }
import org.eclipse.jdt.core.dom.{ ConditionalExpression => JdtConditionalExpression }
import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ FieldAccess => JdtFieldAccess }
import org.eclipse.jdt.core.dom.ITypeBinding
import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }
import org.eclipse.jdt.core.dom.{ InstanceofExpression => JdtInstanceofExpression }
import org.eclipse.jdt.core.dom.{ MethodInvocation => JdtMethodInvocation }
import org.eclipse.jdt.core.dom.{ NullLiteral => JdtNullLiteral }
import org.eclipse.jdt.core.dom.{ NumberLiteral => JdtNumberLiteral }
import org.eclipse.jdt.core.dom.{ ParenthesizedExpression => JdtParenthesizedExpression }
import org.eclipse.jdt.core.dom.{ SimpleName => JdtSimpleName }
import org.eclipse.jdt.core.dom.{ StringLiteral => JdtStringLiteral }
import org.eclipse.jdt.core.dom.{ SuperFieldAccess => JdtSuperFieldAccess }
import org.eclipse.jdt.core.dom.{ SuperMethodInvocation => JdtSuperMethodInvocation }
import org.eclipse.jdt.core.dom.{ ThisExpression => JdtThisExpression }
import org.eclipse.jdt.core.dom.{ TypeLiteral => JdtTypeLiteral }
import org.eclipse.jdt.core.dom.{ VariableDeclarationExpression => JdtVariableDeclarationExpression }

import dwt.jport.ast.AstNode
import dwt.jport.JPorter
import dwt.jport.translators.ImportTranslator

object Expression {
  def toJPort(node: JdtExpression): Expression = {
    node match {
      case n: JdtNumberLiteral => new NumberLiteral(n)
      case n: JdtInfixExpression => new InfixExpression(n)
      case n: JdtArrayAccess => new ArrayAccess(n)
      case n: JdtSimpleName => new SimpleName(n)
      case n: JdtArrayInitializer => new ArrayInitializer(n)
      case n: JdtArrayCreation => new ArrayCreation(n)
      case n: JdtNullLiteral => new NullLiteral(n)
      case n: JdtClassInstanceCreation => new ClassInstanceCreation(n)
      case n: JdtAssignment => new Assignment(n)
      case n: JdtCastExpression => new CastExpression(n)
      case n: JdtCharacterLiteral => new CharacterLiteral(n)
      case n: JdtConditionalExpression => new ConditionalExpression(n)
      case n: JdtBooleanLiteral => new BooleanLiteral(n)
      case n: JdtMethodInvocation => new MethodInvocation(n)
      case n: JdtThisExpression => new ThisExpression(n)
      case n: JdtFieldAccess => new FieldAccess(n)
      case n: JdtInstanceofExpression => new InstanceofExpression(n)
      case n: JdtParenthesizedExpression => new ParenthesizedExpression(n)
      case n: JdtStringLiteral => new StringLiteral(n)
      case n: JdtSuperFieldAccess => new SuperFieldAccess(n)
      case n: JdtSuperMethodInvocation => new SuperMethodInvocation(n)
      case n: JdtTypeLiteral => new TypeLiteral(n)
      case n: JdtVariableDeclarationExpression => new VariableDeclarationExpression(n)
      case _ =>
        assert(node != null)
        JPorter.diagnostic.unhandled(s"Unhandled type ${node.getClass.getName} in ${getClass.getName}")
        null
    }
  }
}

abstract class Expression(node: JdtExpression) extends AstNode(node) with Imports {
  def translate: String

  def imports =
    ImportTranslator.translate(importTypeBindings, declaringClass)
}

trait Imports {
  lazy val importTypeBindings: Seq[ITypeBinding] = Array[ITypeBinding]()
}

class ToJPortExpression(val node: JdtExpression) {
  def toJPort = Expression.toJPort(node)
}

object ExpressionImplicits {
  implicit def ExpressionToJPortExpression(node: JdtExpression) =
    new ToJPortExpression(node)
}
