package dwt.jport.ast

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.IExtendedModifier

import dwt.jport.Symbol
import dwt.jport.analyzers.Modifiers

abstract class BodyDeclaration(node: JdtBodyDeclaration) extends AstNode(node) {
  protected type JavaList[T] = java.util.List[T]

  val modifiers = {
    var mods = node.modifiers.asInstanceOf[JavaList[IExtendedModifier]]
    Modifiers.convert(mods.asScala)
  }
}
