package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ ReturnStatement => JdtReturnStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.Siblings

class ReturnStatement(node: JdtReturnStatement, protected override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement
}
