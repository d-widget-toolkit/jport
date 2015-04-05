package spec.support

import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.AST

trait Parse {
  def parse(source: String, kind: Integer = ASTParser.K_EXPRESSION) = {
    val parser = ASTParser.newParser(AST.JLS8)
    parser.setSource(source.toCharArray())
    parser.setKind(kind)
    parser.createAST(null)
  }
}
