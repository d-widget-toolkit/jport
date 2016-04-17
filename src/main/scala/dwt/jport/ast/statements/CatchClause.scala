package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ CatchClause => JdtCatchClause }
import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.analyzers.JPortConverter
import dwt.jport.analyzers.VisitData
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.ast.AstNode
import dwt.jport.translators.ImportTranslator

class CatchClause(node: JdtCatchClause, private[jport] override val visitData: VisitData)
  extends TypedStatement(node)
  with Siblings {

  def body = JPortConverter.convert(node.getBody, visitData)
  private val exception = node.getException
  val exceptionType = exception.getType.resolveBinding
  val exceptionName = exception.getName.getIdentifier

  val imports =
    ImportTranslator.translate(Array(exceptionType), declaringClass)
}
