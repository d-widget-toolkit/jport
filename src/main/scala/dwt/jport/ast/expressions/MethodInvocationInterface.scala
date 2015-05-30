package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.Type

import dwt.jport.ast.expressions.ExpressionImplicits._

trait MethodInvocationInterface extends Imports {
  private type JavaList[T] = java.util.List[T]

  protected def typedArguments: JavaList[JdtExpression]
  protected def typedTypeArguments: JavaList[Type]

  val arguments = typedArguments.map(_.toJPort)
  val typeArguments = typedTypeArguments.map(_.resolveBinding)
  val name: Expression

  override lazy val importTypeBindings =
    arguments.flatMap(_.importTypeBindings) ++ typeArguments
}
