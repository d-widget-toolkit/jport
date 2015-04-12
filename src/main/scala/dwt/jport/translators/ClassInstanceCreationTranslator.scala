package dwt.jport.translators

import dwt.jport.ast.expressions.ClassInstanceCreation

object ClassInstanceCreationTranslator extends ExpressionTranslator {
  def translate(node: ClassInstanceCreation) =
    s"new ${node.typ}(${node.arguments.mkString(", ")})"
}
