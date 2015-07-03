package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ SuperMethodInvocation => JdtSuperMethodInvocation }
import org.eclipse.jdt.core.dom.Type

import dwt.jport.ast.Invocation
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.SuperMethodInvocationTranslator

class SuperMethodInvocation(node: JdtSuperMethodInvocation)
  extends Expression(node) with Invocation {

  protected def typedArguments =
    node.arguments.asInstanceOf[JavaList[JdtExpression]]

  protected def typedTypeArguments =
    node.typeArguments.asInstanceOf[JavaList[Type]]

  protected def nameExpression = node.getName.toJPort

  override def translate = SuperMethodInvocationTranslator.translate(this)
}
