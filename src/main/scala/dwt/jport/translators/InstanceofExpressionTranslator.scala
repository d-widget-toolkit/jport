package dwt.jport.translators

import dwt.jport.ITypeBindigImplicits._
import dwt.jport.ast.expressions.InstanceofExpression

object InstanceofExpressionTranslator extends ExpressionTranslator {
  def translate(node: InstanceofExpression) =
    s"cast(${node.rightOperand.translate}) ${node.leftOperand.translate}"
}
