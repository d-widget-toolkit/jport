package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }
import org.eclipse.jdt.core.dom.{ MethodDeclaration => JdtMethodDeclaration }
import dwt.jport.ast.TypeDeclaration
import dwt.jport.writers.TypeDeclarationWriter
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.MethodDeclarationWriter
import dwt.jport.ast.MethodDeclaration

class JPortAstVisitor(private val importWriter: ImportWriter) extends ASTVisitor {
  override def visit(node: JdtTypeDeclaration) = {
    TypeDeclarationWriter.write(importWriter, new TypeDeclaration(node))
    true
  }

  override def endVisit(node: JdtTypeDeclaration) = TypeDeclarationWriter.postWrite

  override def visit(node: JdtMethodDeclaration) = {
    MethodDeclarationWriter.write(importWriter, new MethodDeclaration(node))
    true
  }

  override def endVisit(node: JdtMethodDeclaration) = MethodDeclarationWriter.postWrite
}
