package dwt.jport.translators

import dwt.jport.ast.expressions.ArrayInitializer

object ArrayInitializerTranslator extends ExpressionTranslator {
  def translate(node: ArrayInitializer) = s"[${translateInitializers(node)}]"

  private def translateInitializers(node: ArrayInitializer) =
    node.expressions.map(_.translate).mkString(", ")
}
