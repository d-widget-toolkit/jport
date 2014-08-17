package dwt.jport.ast

import org.eclipse.jdt.core.dom.ASTNode

abstract class AstNode[T <: ASTNode](protected val node: T)
