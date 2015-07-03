package dwt.jport.ast

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.Type

import dwt.jport.ast.expressions.Expression
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.ast.expressions.Imports

trait Invocation extends Imports {
  private type JavaList[T] = java.util.List[T]

  protected def typedArguments: JavaList[JdtExpression]
  protected def typedTypeArguments: JavaList[Type]
  protected def nameExpression: Expression

  val arguments = typedArguments.map(_.toJPort)
  val typeArguments = typedTypeArguments.map(_.resolveBinding)
  def name = nameExpression.translate

  override lazy val importTypeBindings =
    arguments.flatMap(_.importTypeBindings) ++ typeArguments
}
