package dwt.jport.analyzers

import scala.collection.JavaConversions._
import com.sun.source.tree.ClassTree
import com.sun.source.tree.IdentifierTree
import com.sun.source.tree.Tree
import com.sun.source.util.Trees
import dwt.jport.writers.ClassWriter
import org.eclipse.jdt.core.dom.TypeDeclaration
import scala.util.DynamicVariable

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
    ClassWriter.write(node.getName.getIdentifier, modifiers, superclass, interfaces)
  }

  def postAnalyze (node: TypeDeclaration): Unit = {

  }

  def modifiers = ""
  def superclass = ""
  def interfaces = new Array[String](0)
}