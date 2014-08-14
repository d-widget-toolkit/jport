package dwt.jport.analyzers

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import dwt.jport.writers.ClassWriter
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }
import scala.util.DynamicVariable
import org.eclipse.jdt.core.dom.SimpleType
import org.eclipse.jdt.core.dom.Type
import org.eclipse.jdt.core.dom.Modifier
import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.TypeParameter
import org.eclipse.jdt.core.dom.SimpleName
import org.eclipse.jdt.core.dom.QualifiedName
import dwt.jport.ast.TypeDeclaration

object ClassAnalyzer
{
  def analyze (node: JdtTypeDeclaration) =
    ClassWriter.write(new TypeDeclaration(node))

  def postAnalyze (node: JdtTypeDeclaration) = ClassWriter.postWrite
}
