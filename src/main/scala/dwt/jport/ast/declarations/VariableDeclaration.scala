package dwt.jport.ast.declarations

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.{ Type => JdtType }
import org.eclipse.jdt.core.dom.VariableDeclarationFragment

import dwt.jport.Symbol
import dwt.jport.Type
import dwt.jport.analyzers.Modifiers
import dwt.jport.ast.Siblings

trait VariableDeclaration extends Siblings {
  private type JavaList[T] = java.util.List[T]

  protected def fragments: JavaList[VariableDeclarationFragment]
  protected def modifiers: JavaList[IExtendedModifier]
  protected def rawType: JdtType
  protected def fragmentBindings = fragments.map(_.resolveBinding)

  val typ = Type.translate(rawType.resolveBinding)
  def names = fragmentBindings.map(e => Symbol.translate(e.getName))

  def translatedModifiers =
    Modifiers.convert(modifiers, variable = true,
      primitiveType = rawType.isPrimitiveType())

  def constantValues = fragmentBindings.map(_.getConstantValue)
}
