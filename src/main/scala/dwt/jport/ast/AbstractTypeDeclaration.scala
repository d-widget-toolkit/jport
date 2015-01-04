package dwt.jport.ast

import org.eclipse.jdt.core.dom.{ AbstractTypeDeclaration => JdtAbstractTypeDeclaration }

abstract class AbstractTypeDeclaration(node: JdtAbstractTypeDeclaration) extends BodyDeclaration(node) {

}
