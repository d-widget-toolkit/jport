package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ ForStatement => JdtForStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class ForStatement(node: JdtForStatement, override val visitData: VisitData)
  extends Statement(node) with Siblings {

  type NodeType = AstNode[ASTNode]

  val body = node.getBody
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

  val hasEmptyBody = body.getNodeType == ASTNode.EMPTY_STATEMENT
}
