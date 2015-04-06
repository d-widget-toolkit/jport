package dwt.jport.translators

import dwt.jport.ast.expressions.ArrayCreation

object ArrayCreationTranslator extends ExpressionTranslator {
  def translate(node: ArrayCreation) =
    if (node.dimensions.length == 0)
      translateInitializer(node)
    else
      "new " + node.elementType + translateDimensions(node)

  private def translateDimensions(node: ArrayCreation) = {
    node.dimensions.length match {
      case 0 => assert(false, "Called translateDimensions with dimensions 0")
      case 1 => translateSingleDimension(node)
      case _ => translateMultiDimension(node)
    }
  }

  private def translateInitializer(node: ArrayCreation) =
    node.initializer.map(_.translate).getOrElse("")

  private def translateSingleDimension(node: ArrayCreation) =
    s"[${node.dimensions.head.translate}]"

  private def translateMultiDimension(node: ArrayCreation) = {
    val dimExprs = node.dimensions.map(_.translate).mkString(", ")
    ("[]" * node.dimensions.length) + s"($dimExprs)"
  }
}
