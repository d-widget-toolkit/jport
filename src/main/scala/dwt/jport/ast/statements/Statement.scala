package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }

import dwt.jport.ast.AstNode

abstract class Statement(node: JdtStatement) extends AstNode(node)
