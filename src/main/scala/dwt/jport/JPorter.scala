package dwt.jport

import scala.io.Source
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.dom.ASTVisitor
import dwt.jport.analyzers.JPortAstVisitor

object JPorter
{
  def port (code: Array[Char]): String = {
    val parser = ASTParser.newParser(AST.JLS8)
    parser.setSource(code)
    parser.setKind(ASTParser.K_COMPILATION_UNIT)
    val compilationUnit = parser.createAST(null)

    compilationUnit.accept(new JPortAstVisitor)
    val result = DCoder.dcoder.result
    DCoder.dcoder.reset()
    result
  }

  def port (code: String): String = port(code.toCharArray())
  def portFromFile (filename: String): String = port(readFile(filename))
  
  private def readFile (filename: String): Array[Char] = {
    Source.fromFile(filename).map(_.toChar).toArray
  }
}
