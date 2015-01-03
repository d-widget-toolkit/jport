package dwt.jport.writers

import scala.collection.JavaConversions._
import dwt.jport.ast.BodyDeclaration

class BodyDeclarationWriter[T <: BodyDeclaration] extends Writer[T] {
  protected def writeModifiers =
    if (node.modifiers.nonEmpty) buffer.append(node.modifiers, ' ')
}
