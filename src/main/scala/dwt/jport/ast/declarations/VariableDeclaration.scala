package dwt.jport.ast.declarations

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.ITypeBinding
import org.eclipse.jdt.core.dom.{ Type => JdtType }
import org.eclipse.jdt.core.dom.VariableDeclarationFragment

import dwt.jport.ITypeBindigImplicits.ITypeBindingToITypeBindingCanonicalType
import dwt.jport.Symbol
import dwt.jport.Type
import dwt.jport.analyzers.Modifiers
import dwt.jport.ast.Siblings
import dwt.jport.translators.ImportTranslator

trait VariableDeclaration extends Siblings {
  private type JavaList[T] = java.util.List[T]

  protected def fragments: JavaList[VariableDeclarationFragment]
  protected def modifiers: JavaList[IExtendedModifier]
  protected def rawType: JdtType
  protected def fragmentBindings = fragments.map(_.resolveBinding)
  protected def node: ASTNode
  protected def declaringClass: ITypeBinding

  private val typeBinding = rawType.resolveBinding
  val typ = Type.translate(typeBinding)

  val imports = {
    val typ = if (typeBinding.isArray())
      typeBinding.getElementType
    else
      typeBinding

    val types = fragments.map(_.getInitializer).filterNot(_ == null).
      map(_.resolveTypeBinding.canonicalType) :+ typ

    ImportTranslator.translate(types, declaringClass)
  }

  def names = fragmentBindings.map(e => Symbol.translate(e.getName))

  def translatedModifiers =
    Modifiers.convert(modifiers, variable = true,
      primitiveType = rawType.isPrimitiveType())

  def constantValues = fragmentBindings.map(_.getConstantValue)
}
