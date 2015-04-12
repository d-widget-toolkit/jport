package dwt.jport.translators

import org.eclipse.jdt.core.dom.ITypeBinding

import dwt.jport.Type

object ImportTranslator {
  def translate(types: Seq[ITypeBinding], declaringClass: ITypeBinding) =
    types.filterNot(e => e.isPrimitive || e.isTypeVariable || e.isNullType()).
      filterNot(_ == declaringClass).
      distinct.map(Type.fullyQualfiedName(_))
}
