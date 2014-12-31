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
import org.eclipse.jdt.core.dom.ITypeBinding
import dwt.jport.Symbol

class TypeDeclaration(node: JdtTypeDeclaration) extends AstNode(node) {
  private type JavaList[T] = java.util.List[T]

  val isInterface = node.isInterface
  val unescapedName = node.getName.getIdentifier
  val name = Symbol.translate(unescapedName)

  private val binding = node.resolveBinding
  private val base = binding.getSuperclass
  private val isJavaLangObject = base.getQualifiedName == "java.lang.Object"

  val imports: Array[String] = {
    val superImport = fullyQualfiedName(base)
    val interfaceImports = binding.getInterfaces.map(fullyQualfiedName(_))

    if (isJavaLangObject) interfaceImports else interfaceImports :+ superImport
  }

  val modifiers = {
    var mods = node.modifiers.asInstanceOf[JavaList[IExtendedModifier]]
    Modifiers.convert(mods.asScala)
  }

  val superclass = if (isJavaLangObject) null else Symbol.translate(base.getName)

  val interfaces = node.superInterfaceTypes.
    map(e => nameOfType(e.asInstanceOf[Type]))

  val typeParameters = typedTypeParameters.map { p =>
    val bounds = simpleTypeBounds(p.typeBounds)
    val typeName = Symbol.translate(p.getName.getIdentifier)
    (typeName, namesOfBounds(bounds))
  }

  val unboundTypeParameters = typeParameters.filter(_._2.isEmpty)
  val singleBoundTypeParameters = typeParameters.filter(_._2.length == 1)
  val multipleBoundTypeParameters = typeParameters.filter(_._2.length >= 2)

  val hasMembers = !node.bodyDeclarations.isEmpty

  private def typedTypeParameters = node.typeParameters.
    asInstanceOf[JavaList[TypeParameter]]

  private def simpleTypeBounds(bounds: JavaList[_]) =
    bounds.asInstanceOf[JavaList[Type]].
      filter(_.isSimpleType).map(_.asInstanceOf[SimpleType])

  private def namesOfBounds(bounds: Buffer[SimpleType]) = bounds.map { e =>
    e.getName match {
      case s: SimpleName => Symbol.translate(s.getIdentifier)
      case q: QualifiedName => Symbol.translate(q.getFullyQualifiedName)
      case t => throw new Exception(s"Unhalded bounds type '$t'")
    }
  }

  private def nameOfType(typ: Type): String = {
    if (typ == null) return null

    if (typ.isSimpleType)
      return Symbol.translate(typ.asInstanceOf[SimpleType].getName.getFullyQualifiedName)

    else null
  }

  private def fullyQualfiedName(binding: ITypeBinding): String = {
    val pac = binding.getPackage
    val name = binding.getName
    val fullyQualfiedName = if (pac.isUnnamed) name else pac.getName + "." + name
    Symbol.translate(fullyQualfiedName)
  }
}
