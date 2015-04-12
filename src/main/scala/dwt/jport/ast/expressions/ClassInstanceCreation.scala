package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ ClassInstanceCreation => JdtClassInstanceCreation }
import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }

import dwt.jport.Type
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.core.JavaListImplicits._
import dwt.jport.translators.ClassInstanceCreationTranslator

class ClassInstanceCreation(node: JdtClassInstanceCreation)
  extends Expression(node) {

  private val typedArguments = node.arguments.typed[JdtExpression]
  val arguments = typedArguments.map(_.toJPort.translate)
  val typ = Type.translate(node.resolveTypeBinding)

  def translate = ClassInstanceCreationTranslator.translate(this)
}
