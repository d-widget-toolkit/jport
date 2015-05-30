package dwt.jport.translators

import dwt.jport.ITypeBindigImplicits._

import dwt.jport.ast.expressions.SuperMethodInvocation

object SuperMethodInvocationTranslator extends MethodInvocationTranslatorBase {
  def translate(node: SuperMethodInvocation) = "super." + super.translate(node)
}
