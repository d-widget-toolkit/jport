package dwt.jport.ast

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.{ FieldDeclaration => JdtFieldDeclaration }
import org.eclipse.jdt.core.dom.VariableDeclarationFragment

import dwt.jport.Symbol
import dwt.jport.Type
import dwt.jport.analyzers.Modifiers
import dwt.jport.analyzers.VisitData

class FieldDeclaration(node: JdtFieldDeclaration, protected override val visitData: VisitData[JdtBodyDeclaration])
  extends BodyDeclaration(node)
  with Siblings[JdtBodyDeclaration] {

  private val fragments = node.fragments.asInstanceOf[JavaList[VariableDeclarationFragment]]
  private val fragmentBindings = fragments.map(_.resolveBinding)

  val typ = Type.translate(node.getType.resolveBinding)
  val names = fragmentBindings.map(e => Symbol.translate(e.getName))

  override val translatedModifiers =
    Modifiers.convert(modifiers, variable = true,
      primitiveType = node.getType.isPrimitiveType())
}
