package dwt.jport.ast

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer

import org.eclipse.jdt.core.dom.{ AbstractTypeDeclaration => JdtAbstractTypeDeclaration }
import org.eclipse.jdt.core.dom.ITypeBinding
import org.eclipse.jdt.core.dom.QualifiedName
import org.eclipse.jdt.core.dom.SimpleName
import org.eclipse.jdt.core.dom.SimpleType
import org.eclipse.jdt.core.dom.{ Type => JdtType }
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }
import org.eclipse.jdt.core.dom.TypeParameter

import dwt.jport.Symbol
import dwt.jport.Type
import dwt.jport.analyzers.VisitData

class TypeDeclaration(node: JdtTypeDeclaration, protected override val visitData: VisitData[JdtAbstractTypeDeclaration])
  extends BodyDeclaration(node)
  with TypeParameters
  with Siblings {

  type JdtNodeType = JdtAbstractTypeDeclaration

  import Type.fullyQualfiedName

  val isInterface = node.isInterface
  val unescapedName = node.getName.getIdentifier
  val name = Symbol.translate(unescapedName)

  private lazy val binding = node.resolveBinding
  private val base = Option(binding.getSuperclass)
  private val isJavaLangObject = base.map(_.getQualifiedName).exists(_ == "java.lang.Object")

  val imports: Array[String] = {
    val superImport = base.map(fullyQualfiedName(_))
    val interfaceImports = binding.getInterfaces.map(fullyQualfiedName(_))

    if (isJavaLangObject) interfaceImports else interfaceImports ++ superImport
  }

  val superclass = if (isJavaLangObject) None else Symbol.translate(base.map(_.getName))

  val interfaces = node.superInterfaceTypes.
    map(e => nameOfType(e.asInstanceOf[JdtType]))

  val hasMembers = !node.bodyDeclarations.isEmpty

  protected override def typeParametersBinding = binding.getTypeParameters

  private def nameOfType(typ: JdtType): String = {
    if (typ == null) return null

    if (typ.isSimpleType)
      return Symbol.translate(typ.asInstanceOf[SimpleType].getName.getFullyQualifiedName)

    else null
  }
}
