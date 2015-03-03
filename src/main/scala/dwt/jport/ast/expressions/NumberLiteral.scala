package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ NumberLiteral => JdtNumberLiteral }
import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.translators.NumberLiteralTranslator

class NumberLiteral(node: JdtNumberLiteral)
  extends Expression(node) {

  val token = node.getToken
  override def translate = NumberLiteralTranslator.translate(this)
}
