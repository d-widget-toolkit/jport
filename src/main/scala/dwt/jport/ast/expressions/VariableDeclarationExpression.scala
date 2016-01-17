package dwt.jport.ast.expressions

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.{ VariableDeclarationExpression => JdtVariableDeclarationExpression }
import org.eclipse.jdt.core.dom.VariableDeclarationFragment
import dwt.jport.ast.declarations.VariableDeclaration
import dwt.jport.translators.VariableDeclarationExpressionTranslator
import dwt.jport.analyzers.VisitData

class VariableDeclarationExpression(node: JdtVariableDeclarationExpression)
  extends Expression(node)
  with VariableDeclaration {

  type NodeType = Expression

  private[jport] override def visitData =
    new VisitData[Expression](false, None, None)

  override def modifiers = new java.util.ArrayList[IExtendedModifier]

  protected override def fragments = node.fragments.asInstanceOf[JavaList[VariableDeclarationFragment]]
  protected override def rawType = node.getType

  override def imports = super[VariableDeclaration].imports

  override def translate =
    VariableDeclarationExpressionTranslator.translate(this)
}
