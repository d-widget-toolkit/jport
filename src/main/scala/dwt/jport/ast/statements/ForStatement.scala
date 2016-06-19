package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ ForStatement => JdtForStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.analyzers.JPortConverter

class ForStatement(node: JdtForStatement, override val visitData: VisitData)
  extends Statement(node) with Siblings {

  //  lazy val body = extractBody(node.getBody, visitData)
  lazy val body = JPortConverter.convert(node.getBody, visitData).asInstanceOf[Block]
  val expression = Option(node.getExpression).map(_.toJPort)

  private val typedInitiailzers =
    node.initializers.asInstanceOf[JavaList[JdtExpression]]

  val initializers = typedInitiailzers.map(_.toJPort)

  private val typedUpdaters =
    node.updaters().asInstanceOf[JavaList[JdtExpression]]

  val updaters = typedUpdaters.map(_.toJPort)

  val imports = initializers.flatMap(_.imports) ++
    updaters.flatMap(_.imports) ++
    expression.map(_.imports).getOrElse(Seq())

  lazy val hasEmptyBody = body.nodeType == ASTNode.EMPTY_STATEMENT

  override lazy val hasSingleStatementBody = body.statements.length == 1

  override def canonicalize() = {
    canonicalizeBody(node.getBody, node.setBody(_))
    this
  }
}
