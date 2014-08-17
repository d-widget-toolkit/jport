package dwt.jport

import collection.JavaConversions._
import scala.io.Source
import scala.util.DynamicVariable

import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.compiler.IProblem

import dwt.jport.core.JPortAny._
import dwt.jport.analyzers.JPortAstVisitor

object JPorter {
  def port(code: Array[Char], filename: Option[String] = Option.empty[String]): String = (new JPorter(filename)).port(code)

  def port(code: String): String = port(code.toCharArray())

  def portFromFile(filename: String): String =
    port(readFile(filename), Option(filename))

  private def readFile(filename: String): Array[Char] =
    Source.fromFile(filename).map(_.toChar).toArray
}

class JPorter(private val filename: Option[String]) {
  private var _parser: ASTParser = null
  private val diagnostic = new Diagnostic

  def port(code: Array[Char]): String = {
    parser.setUnitName(filename.getOrElse(null))
    parser.setSource(code)
    val unit = parser.createAST(null).asInstanceOf[CompilationUnit]
    checkCompilationErrors(unit)

    if (diagnostic.hasDiagnostics)
      return null

    DCoder.dcoder.reset()
    unit.accept(new JPortAstVisitor)
    DCoder.dcoder.result
  }

  private def parser: ASTParser = {
    if (_parser != null) return _parser

    _parser = ASTParser.newParser(AST.JLS8)
    _parser.setResolveBindings(true)
    _parser.setCompilerOptions(compilerOptions)
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

  private def checkCompilationErrors(unit: CompilationUnit): Unit = {
    for (e <- unit.getProblems.filter(_.isError))
      diagnostic.error(filename.getOrElse(""), e.getSourceLineNumber,
        e.getMessage)
  }
}
