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
import org.eclipse.jdt.core.dom.ITypeBinding
import org.eclipse.jdt.core.dom.{ InfixExpression => JdtInfixExpression }
import org.eclipse.jdt.core.dom.{ NullLiteral => JdtNullLiteral }
import org.eclipse.jdt.core.dom.{ NumberLiteral => JdtNumberLiteral }
import org.eclipse.jdt.core.dom.{ SimpleName => JdtSimpleName }

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
      case _ =>
        JPorter.diagnostic.unhandled(s"Unhandled type ${node.getClass.getName} in ${getClass.getName}")
        null
    }
  }
}

abstract class Expression(node: JdtExpression) extends AstNode(node) {
  def translate: String

  lazy val importTypeBindings = Array[ITypeBinding]()
  lazy val imports =
    ImportTranslator.translate(importTypeBindings, declaringClass)
}

class ToJPortExpression(val node: JdtExpression) {
  def toJPort = Expression.toJPort(node)
}

object ExpressionImplicits {
  implicit def ExpressionToJPortExpression(node: JdtExpression) =
    new ToJPortExpression(node)
}
