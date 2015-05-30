package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ SuperMethodInvocation => JdtSuperMethodInvocation }
import org.eclipse.jdt.core.dom.Type

import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.SuperMethodInvocationTranslator

class SuperMethodInvocation(node: JdtSuperMethodInvocation)
  extends Expression(node) with MethodInvocationInterface {

  protected override def typedArguments =
    node.arguments.asInstanceOf[JavaList[JdtExpression]]

  protected override def typedTypeArguments =
    node.typeArguments.asInstanceOf[JavaList[Type]]

  override val name = node.getName.toJPort

  override def translate = SuperMethodInvocationTranslator.translate(this)
}
