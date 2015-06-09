package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ EmptyStatement => JdtEmptyStatement }
import org.eclipse.jdt.core.dom.Statement

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings

class EmptyStatement(node: JdtEmptyStatement, protected override val visitData: VisitData[Statement])
  extends AstNode(node)
  with Siblings {

  type JdtNodeType = Statement
}
