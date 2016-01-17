package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.Expression

import org.eclipse.jdt.core.dom.{ SuperConstructorInvocation => JdtSuperConstructorInvocation }
import org.eclipse.jdt.core.dom.Type

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Invocation
import dwt.jport.ast.Siblings

class SuperConstructorInvocation(node: JdtSuperConstructorInvocation, private[jport] override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings
  with Invocation {

  type NodeType = Statement

  protected override def declaringClass = super[Statement].declaringClass

  protected def typedArguments =
    node.arguments.asInstanceOf[JavaList[Expression]]

  protected def typedTypeArguments =
    node.typeArguments.asInstanceOf[JavaList[Type]]

  protected def nameExpression = ??? // should never be called
  override def name = "super"
}
