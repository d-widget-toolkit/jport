package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }

import dwt.jport.ast.TypeDeclaration
import dwt.jport.writers.TypeDeclarationWriter

class JPortAstVisitor extends ASTVisitor {
  override def visit(node: JdtTypeDeclaration) = {
    TypeDeclarationWriter.write(new TypeDeclaration(node))
    true
  }

  override def endVisit(node: JdtTypeDeclaration) = TypeDeclarationWriter.postWrite
}
