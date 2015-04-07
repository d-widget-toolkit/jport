package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ NullLiteral => JdtNullLiteral }
import dwt.jport.translators.NullLiteralTranslator

class NullLiteral(node: JdtNullLiteral)
  extends Expression(node) {

  override def translate = NullLiteralTranslator.translate(this)
}
