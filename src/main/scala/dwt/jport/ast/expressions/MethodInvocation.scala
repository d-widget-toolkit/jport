package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ MethodInvocation => JdtMethodInvocation }
import org.eclipse.jdt.core.dom.Type

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.MethodInvocationTranslator

class MethodInvocation(node: JdtMethodInvocation) extends Expression(node) {
  private val typedArguments =
    node.arguments.asInstanceOf[JavaList[JdtExpression]]

  private val typedTypeArguments =
    node.typeArguments.asInstanceOf[JavaList[Type]]

  val arguments = typedArguments.map(_.toJPort)
  val typeArguments = typedTypeArguments.map(_.resolveBinding)
  val name = node.getName.toJPort
  val expression = node.getExpression.toJPort

  override lazy val importTypeBindings =
    arguments.flatMap(_.importTypeBindings) ++ typeArguments

  override def translate = MethodInvocationTranslator.translate(this)
}
