package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ ConstructorInvocation => JdtConstructorInvocation }
import org.eclipse.jdt.core.dom.Expression
import org.eclipse.jdt.core.dom.Type

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Invocation
import dwt.jport.ast.Siblings

class ConstructorInvocation(node: JdtConstructorInvocation, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings
  with Invocation {

  protected def typedArguments =
    node.arguments.asInstanceOf[JavaList[Expression]]

  protected def typedTypeArguments =
    node.typeArguments.asInstanceOf[JavaList[Type]]

  protected def nameExpression = ??? // should never be called
  override def name = "this"
}
