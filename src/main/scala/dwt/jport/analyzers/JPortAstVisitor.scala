package dwt.jport.analyzers

import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.TypeDeclaration

class JPortAstVisitor extends ASTVisitor
{
  override def visit (node: TypeDeclaration): Boolean = {
    if (!node.isInterface)
      ClassAnalyzer.analyze(node)

    true
  }

  override def endVisit (node: TypeDeclaration): Unit = {
    if (!node.isInterface)
      ClassAnalyzer.postAnalyze(node)
  }
}
