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
}
