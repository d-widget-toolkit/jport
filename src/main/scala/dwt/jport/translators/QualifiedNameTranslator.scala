package dwt.jport.translators

import dwt.jport.Symbol

import dwt.jport.ast.expressions.QualifiedName

object QualifiedNameTranslator extends ExpressionTranslator {
  def translate(node: QualifiedName) =
    Symbol.translate(node.qualifier.translate) + "." +
      Symbol.translate(node.name.translate)
}
