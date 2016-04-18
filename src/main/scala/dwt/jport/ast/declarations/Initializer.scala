package dwt.jport.ast.declarations

import org.eclipse.jdt.core.dom.{ Initializer => JdtInitializer }

import dwt.jport.JPorter
import dwt.jport.analyzers.JPortConverter
import dwt.jport.analyzers.VisitData
import dwt.jport.ast.BodyDeclaration
import dwt.jport.ast.Siblings

class Initializer(node: JdtInitializer, private[jport] override val visitData: VisitData)
  extends BodyDeclaration(node)
  with Siblings {

  validate

  override def isMultiline = true
  lazy val body = JPortConverter.convert(node.getBody, visitData)

  private def validate: Unit = if (!isStatic) JPorter.diagnostic.unhandled(
    s"unhandled node ${node.getClass.getName} in ${getClass.getName}")
}
