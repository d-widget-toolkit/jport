package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ TypeDeclarationStatement => JdtTypeDeclarationStatement }

import dwt.jport.analyzers.JPortConverter
import dwt.jport.analyzers.VisitData

import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class TypeDeclarationStatement(node: JdtTypeDeclarationStatement, private[jport] override val visitData: VisitData)
  extends Statement(node)
  with Siblings {

  val declaration = JPortConverter.convert(node.getDeclaration, visitData)
}
