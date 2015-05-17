package dwt.jport

import org.eclipse.jdt.core.dom.ITypeBinding

object Type {
  def translate(binding: ITypeBinding): String = {
    val name = binding.getName

    if (binding.isPrimitive())
      return name
    else
      return Symbol.translate(name)
  }

  def fullyQualfiedName(binding: ITypeBinding): String = {
    val pac = binding.getPackage
    assert(pac != null)

    val name = binding.getName
    val fullyQualfiedName = if (pac.isUnnamed) name else pac.getName + "." + name
    Symbol.translate(fullyQualfiedName)
  }

  def canonicalType(binding: ITypeBinding) = if (binding.isArray()) binding.getElementType
}

class ITypeBindingCanonicalType(val binding: ITypeBinding) {
  def canonicalType = if (binding.isArray()) binding.getElementType else binding
}

class ITypeBindingTranslate(val binding: ITypeBinding) {
  def translate = Type.translate(binding)
}

object ITypeBindigImplicits {
  implicit def ITypeBindingToITypeBindingCanonicalType(binding: ITypeBinding) =
    new ITypeBindingCanonicalType(binding)

  implicit def ITypeBindingToITypeBindingTranslate(binding: ITypeBinding) =
    new ITypeBindingTranslate(binding)
}
