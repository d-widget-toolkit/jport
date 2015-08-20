package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ EmptyStatement => JdtEmptyStatement }

import dwt.jport.analyzers.JPortConverter
import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings

class EmptyStatement(node: JdtEmptyStatement, protected override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement

  override val parent = Option(node.getParent).
    map(JPortConverter.convert[ASTNode, AstNode[ASTNode]](_))
}
