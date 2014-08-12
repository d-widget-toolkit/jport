package dwt.jport.analyzers

import scala.collection.JavaConversions._
import dwt.jport.writers.ClassWriter
import org.eclipse.jdt.core.dom.TypeDeclaration
import scala.util.DynamicVariable
import org.eclipse.jdt.core.dom.SimpleType
import org.eclipse.jdt.core.dom.Type

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
  private var node: TypeDeclaration = null

  def analyze (node: TypeDeclaration): Unit = {
    this.node = node

    ClassWriter.write(node.getName.getIdentifier, modifiers, superclass,
      interfaces, !node.bodyDeclarations.isEmpty)
  }

  def postAnalyze (node: TypeDeclaration): Unit = {

  }

  private def modifiers = ""

  private def superclass: String = nameOfType(node.getSuperclassType)

  private def interfaces = node.superInterfaceTypes.
    map(e => nameOfType(e.asInstanceOf[Type]))

  private def nameOfType (typ: Type): String = {
    if (typ == null) return null

    if (typ.isSimpleType)
      return typ.asInstanceOf[SimpleType].getName.getFullyQualifiedName

    else null
  }
}
