package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.VariableDeclarationFragment
import org.eclipse.jdt.core.dom.{ VariableDeclarationStatement => JdtVariableDeclarationStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.declarations.VariableDeclaration
import dwt.jport.ast.expressions.Expression

class VariableDeclarationStatement(node: JdtVariableDeclarationStatement, private[jport] override val visitData: VisitData[Statement])
  extends Statement(node)
  with VariableDeclaration {

  type NodeType = Statement

  override def isMultiline = false
  override def modifiers = new java.util.ArrayList[IExtendedModifier]

  protected override def fragments = node.fragments.asInstanceOf[JavaList[VariableDeclarationFragment]]
  protected override def rawType = node.getType
}
