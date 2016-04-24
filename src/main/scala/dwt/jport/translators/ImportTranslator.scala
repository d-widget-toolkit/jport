package dwt.jport.translators

import org.eclipse.jdt.core.dom.ITypeBinding

import dwt.jport.Type

object ImportTranslator {
  def translate(types: Seq[ITypeBinding], declaringClass: ITypeBinding) =
    types.filterNot(e => isPrimitive(e) || e.isTypeVariable || e.isNullType).
      filterNot(_ == declaringClass).
      filterNot(Type.isBuiltIn(_, "Class")).
      map(elementType).
      distinct.map(Type.fullyQualfiedName(_))

  private def isPrimitive(t: ITypeBinding): Boolean =
    if (t.isArray) isPrimitive(t.getElementType) else t.isPrimitive

  private def elementType(t: ITypeBinding): ITypeBinding =
    if (t.isArray) elementType(t.getElementType) else t
}
