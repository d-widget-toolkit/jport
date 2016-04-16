package dwt.jport.ast

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.Modifier

import dwt.jport.Symbol
import dwt.jport.analyzers.Modifiers

abstract class BodyDeclaration(node: JdtBodyDeclaration) extends AstNode(node) {
  def modifiers = node.modifiers.asInstanceOf[JavaList[IExtendedModifier]]
  def translatedModifiers = Modifiers.convert(modifiers)

  private val modifierFlags = node.getModifiers

  val isFinal = Modifier.isFinal(modifierFlags)
  val isPrivate = Modifier.isPrivate(modifierFlags)
  val isStatic = Modifier.isStatic(modifierFlags)
}
