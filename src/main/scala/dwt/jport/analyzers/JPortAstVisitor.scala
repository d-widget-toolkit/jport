package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.{ TypeDeclaration => JdtTypeDeclaration }

import dwt.jport.writers.ClassWriter
import dwt.jport.ast.TypeDeclaration

class JPortAstVisitor extends ASTVisitor
{
  override def visit (node: JdtTypeDeclaration) = {
    ClassWriter.write(new TypeDeclaration(node))
    true
  }

  override def endVisit (node: JdtTypeDeclaration) = ClassWriter.postWrite
}
