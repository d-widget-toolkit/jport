package dwt.jport

import scala.io.Source
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.dom.ASTVisitor
import dwt.jport.analyzers.JPortAstVisitor

class JPorter (private val filename: String)
{
  def port() : Unit = {
    val parser = ASTParser.newParser(AST.JLS8)
    parser.setSource(readFile(filename))
    parser.setKind(ASTParser.K_COMPILATION_UNIT)
    val compilationUnit = parser.createAST(null)

    compilationUnit.accept(new JPortAstVisitor)
  }

  private def readFile (filename: String): Array[Char] = {
    Source.fromFile(filename).map(_.toChar).toArray
  }
}
