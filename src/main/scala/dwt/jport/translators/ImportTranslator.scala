package dwt.jport.translators

import org.eclipse.jdt.core.dom.ITypeBinding

import dwt.jport.Type

object ImportTranslator {
  def translate(types: Seq[ITypeBinding]) =
    types.filterNot(e => e.isPrimitive || e.isTypeVariable || e.isNullType()).
      distinct.map(Type.fullyQualfiedName(_))
}
