package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ QualifiedName => JdtQualifiedName }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.QualifiedNameTranslator

class QualifiedName(node: JdtQualifiedName) extends Expression(node) {
  val name = node.getName.toJPort
  val qualifier = node.getQualifier.toJPort

  def translate = QualifiedNameTranslator.translate(this)
}
