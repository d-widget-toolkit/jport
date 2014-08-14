package dwt.jport.ast

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer

import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.Modifier
import org.eclipse.jdt.core.dom.Type
import org.eclipse.jdt.core.dom.SimpleType
import org.eclipse.jdt.core.dom.SimpleName
import org.eclipse.jdt.core.dom.QualifiedName
import org.eclipse.jdt.core.dom.TypeParameter

import dwt.jport.analyzers.Modifiers
import dwt.jport.ast.AstNode

class TypeDeclaration(node: JdtTypeDeclaration) extends AstNode(node) {
  private type JavaList[T] = java.util.List[T]

  val isInterface = node.isInterface
  val name = node.getName.getIdentifier

  val modifiers = {
    var mods = node.modifiers.asInstanceOf[JavaList[IExtendedModifier]]
    Modifiers.convert(mods.asScala)
  }

  val superclass = nameOfType(node.getSuperclassType)

  val interfaces = node.superInterfaceTypes.
    map(e => nameOfType(e.asInstanceOf[Type]))

  val typeParameters = typedTypeParameters.map { p =>
    val bounds = simpleTypeBounds(p.typeBounds)
    (p.getName.getIdentifier, namesOfBounds(bounds))
  }

  val unboundTypeParameters = typeParameters.filter(_._2.isEmpty)
  val singleBoundTypeParameters = typeParameters.filter(_._2.length == 1)
  val multipleBoundTypeParameters = typeParameters.filter(_._2.length >= 2)

  val hasMembers = !node.bodyDeclarations.isEmpty

  private def typedTypeParameters = node.typeParameters.
    asInstanceOf[JavaList[TypeParameter]]

  private val modifierMapping = Map(
    Modifier.ABSTRACT -> "abstract",
    Modifier.FINAL -> "final",
    Modifier.PRIVATE -> "private",
    Modifier.PROTECTED -> "protected",
    Modifier.PUBLIC -> "public",
    Modifier.STATIC -> "static",
    Modifier.SYNCHRONIZED -> "synchronized",
    Modifier.VOLATILE -> "volatile"
  )

  private def simpleTypeBounds(bounds: JavaList[_]) =
    bounds.asInstanceOf[JavaList[Type]].
    filter(_.isSimpleType).map(_.asInstanceOf[SimpleType])

  private def namesOfBounds(bounds: Buffer[SimpleType]) = bounds.map { e =>
    e.getName match {
      case s: SimpleName => s.getIdentifier
      case q: QualifiedName => q.getQualifier + "." + q.getName
      case t => throw new Exception(s"Unhalded bounds type '$t'")
    }
  }

  private def nameOfType(typ: Type): String = {
    if (typ == null) return null

    if (typ.isSimpleType)
      return typ.asInstanceOf[SimpleType].getName.getFullyQualifiedName

    else null
  }
}