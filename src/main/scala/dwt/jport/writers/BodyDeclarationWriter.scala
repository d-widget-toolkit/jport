package dwt.jport.writers

import scala.collection.JavaConversions._
import dwt.jport.ast.BodyDeclaration

abstract class BodyDeclarationWriter[T <: BodyDeclaration] extends Writer[T] {
  protected def writeModifiers =
    if (node.translatedModifiers.nonEmpty)
      buffer.append(node.translatedModifiers, ' ')
}
