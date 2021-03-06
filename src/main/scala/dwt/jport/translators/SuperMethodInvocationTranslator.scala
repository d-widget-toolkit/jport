package dwt.jport.translators

import dwt.jport.ITypeBindigImplicits._

import dwt.jport.ast.expressions.SuperMethodInvocation

object SuperMethodInvocationTranslator extends InvocationTranslator {
  def translate(node: SuperMethodInvocation) = "super." + super.translate(node)
}
