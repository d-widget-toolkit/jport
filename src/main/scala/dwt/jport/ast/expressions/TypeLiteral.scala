package dwt.jport.ast.expressions

import scala.collection.mutable.Buffer

import org.eclipse.jdt.core.dom.{ TypeLiteral => JdtTypeLiteral }

import dwt.jport.translators.TypeLiteralTranslator

class TypeLiteral(node: JdtTypeLiteral) extends Expression(node) {
  override val typeBinding = node.getType.resolveBinding

  override def translate = TypeLiteralTranslator.translate(this)

  override lazy val importTypeBindings = Buffer(typeBinding)
}
