package dwt.jport.analyzers

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import dwt.jport.writers.ClassWriter
import org.eclipse.jdt.core.dom.TypeDeclaration
import scala.util.DynamicVariable
import org.eclipse.jdt.core.dom.SimpleType
import org.eclipse.jdt.core.dom.Type
import org.eclipse.jdt.core.dom.Modifier
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.TypeParameter

object ClassAnalyzer
{
  private val analyzer = new DynamicVariable[ClassAnalyzer](new ClassAnalyzer)

  def analyze (node: TypeDeclaration): Unit = {
    analyzer.value.analyze(node)
  }

  def postAnalyze (node: TypeDeclaration): Unit = {
    analyzer.value.postAnalyze(node)
  }
}

class ClassAnalyzer
{
  private type JavaList[T] = java.util.List[T]

  private var node: TypeDeclaration = null

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

  def analyze (node: TypeDeclaration): Unit = {
    this.node = node

    ClassWriter.write(node.getName.getIdentifier, modifiers, superclass,
      interfaces, generics, !node.bodyDeclarations.isEmpty)
  }

  def postAnalyze (node: TypeDeclaration): Unit = {

  }

  private def modifiers: String = {
    val mods = node.modifiers.asInstanceOf[java.util.List[IExtendedModifier]]
    Modifiers.convert(mods.asScala)
  }

  private def superclass: String = nameOfType(node.getSuperclassType)

  private def interfaces = node.superInterfaceTypes.
    map(e => nameOfType(e.asInstanceOf[Type]))

  private def nameOfType (typ: Type): String = {
    if (typ == null) return null

    if (typ.isSimpleType)
      return typ.asInstanceOf[SimpleType].getName.getFullyQualifiedName

    else null
  }

  def generics =
    node.typeParameters.asInstanceOf[JavaList[TypeParameter]].
      map(_.getName.toString)
}
