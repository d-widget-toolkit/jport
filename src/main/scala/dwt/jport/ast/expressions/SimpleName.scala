package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ SimpleName => JdtSimpleName }
import dwt.jport.Symbol

class SimpleName(node: JdtSimpleName) extends Expression(node) {
  val identifier = node.getIdentifier

  override def translate = Symbol.translate(identifier)
}
