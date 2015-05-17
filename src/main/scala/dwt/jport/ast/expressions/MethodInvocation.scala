package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ MethodInvocation => JdtMethodInvocation }

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.MethodInvocationTranslator

class MethodInvocation(node: JdtMethodInvocation) extends Expression(node) {
  val name = node.getName.toJPort

  private val typedArguments =
    node.arguments().asInstanceOf[JavaList[JdtExpression]]

  val arguments = typedArguments.map(_.toJPort)

  override def translate = MethodInvocationTranslator.translate(this)
}
