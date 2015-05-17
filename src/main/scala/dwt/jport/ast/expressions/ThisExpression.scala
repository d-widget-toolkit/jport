package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ ThisExpression => JdtThisExpression }

import dwt.jport.translators.ThisExpressionTranslator

class ThisExpression(node: JdtThisExpression) extends Expression(node) {
  override def translate = ThisExpressionTranslator.translate(this)
}
