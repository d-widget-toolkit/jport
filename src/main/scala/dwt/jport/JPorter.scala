package dwt.jport

import scala.collection.JavaConversions._
import scala.io.Source

import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.{ CompilationUnit => JdtCompilationUnit }

import dwt.jport.analyzers.CompilationUnit
import dwt.jport.core.JPortAny._
import dwt.jport.util.ThreadLocalVariable

object JPorter {
  def compilationUnit = _compilationUnit

  private var _compilationUnit: ThreadLocalVariable[CompilationUnit] = null

  private val filename: String = null
  private val classpathEntries = Array(".")
  private val sourcepathEntries = Array(".")
  private val includeRunningVMBootclasspath = true

  def port(code: Array[Char], filename: String, sourcepathEntries: Array[String],
    classpathEntries: Array[String], includeRunningVMBootclasspath: Boolean): String =
    new JPorter(filename, sourcepathEntries, classpathEntries, includeRunningVMBootclasspath).port(code)

  def port(code: String, filename: String = JPorter.filename,
    sourcepathEntries: Array[String] = JPorter.sourcepathEntries,
    classpathEntries: Array[String] = JPorter.classpathEntries,
    includeRunningVMBootclasspath: Boolean = JPorter.includeRunningVMBootclasspath): String =
    port(code.toCharArray(), filename, sourcepathEntries, classpathEntries, includeRunningVMBootclasspath)

  def portFromFile(filename: String,
    sourcepathEntries: Array[String] = JPorter.sourcepathEntries,
    classpathEntries: Array[String] = JPorter.classpathEntries,
    includeRunningVMBootclasspath: Boolean = JPorter.includeRunningVMBootclasspath): String =
    port(readFile(filename), filename, sourcepathEntries, classpathEntries, includeRunningVMBootclasspath)

  private def readFile(filename: String): Array[Char] =
    Source.fromFile(filename).map(_.toChar).toArray
}

class JPorter(val filename: String = JPorter.filename,
  val sourcepathEntries: Array[String] = JPorter.sourcepathEntries,
  val classpathEntries: Array[String] = JPorter.classpathEntries,
  val includeRunningVMBootclasspath: Boolean = JPorter.includeRunningVMBootclasspath) {

  private var _parser: ASTParser = null
  private val diagnostic = new Diagnostic

  def port(code: Array[Char]): String = {
    parser.setSource(code)
    val unit = parser.createAST(null).asInstanceOf[JdtCompilationUnit]
    checkCompilationErrors(unit)

    if (diagnostic.hasDiagnostics)
      return null

    val cu = (new CompilationUnit(unit))
    JPorter._compilationUnit = new ThreadLocalVariable(cu)
    cu.process()
  }

  private def parser: ASTParser = {
    if (_parser != null) return _parser

    _parser = ASTParser.newParser(AST.JLS8)
    _parser.setUnitName(filename)
    _parser.setResolveBindings(true)
    _parser.setCompilerOptions(compilerOptions)
    _parser.setEnvironment(classpathEntries, sourcepathEntries, null,
      includeRunningVMBootclasspath)
    //parser.setKind(ASTParser.K_COMPILATION_UNIT)
    _parser
  }

  private def compilerOptions = defaultCompilerOptions

  private def defaultCompilerOptions: Map[String, String] = {
    Map(
      JavaCore.COMPILER_SOURCE -> "1.7",
      JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM -> "1.7",
      JavaCore.COMPILER_COMPLIANCE -> "1.7",
      JavaCore.COMPILER_DOC_COMMENT_SUPPORT -> "enabled")
  }

  private def checkCompilationErrors(unit: JdtCompilationUnit): Unit = {
    for (e <- unit.getProblems.filter(_.isError))
      diagnostic.error(filename, e.getSourceLineNumber,
        e.getMessage)
  }
}
