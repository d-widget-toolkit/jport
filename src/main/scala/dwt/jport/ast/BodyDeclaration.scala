package dwt.jport.ast

import scala.collection.JavaConversions._
import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.IExtendedModifier
import dwt.jport.Symbol
import dwt.jport.analyzers.Modifiers
import org.eclipse.jdt.core.dom.Modifier

abstract class BodyDeclaration(node: JdtBodyDeclaration) extends AstNode(node) {
  protected type JavaList[T] = java.util.List[T]

  val modifiers = node.modifiers.asInstanceOf[JavaList[IExtendedModifier]]
  val translatedModifiers = Modifiers.convert(modifiers)

  private val modifierFlags = node.getModifiers

  val isFinal = Modifier.isFinal(modifierFlags)
  val isPrivate = Modifier.isPrivate(modifierFlags)
  val isStatic = Modifier.isStatic(modifierFlags)
}
