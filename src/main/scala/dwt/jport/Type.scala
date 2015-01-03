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
}