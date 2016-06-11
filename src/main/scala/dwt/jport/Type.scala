package dwt.jport

import org.eclipse.jdt.core.dom.ITypeBinding

object Type {
  def translate(binding: ITypeBinding): String = {
    if (binding.isPrimitive())
      translatePrimitive(binding)
    else {
      if (isJavaLangType(binding))
        translateJavaLangType(binding)
      else
        Symbol.translate(binding.getName)
    }
  }

  def fullyQualfiedName(binding: ITypeBinding): String = {
    val pac = binding.getPackage
    assert(pac != null)

    val name = binding.getName
    val fullyQualfiedName = if (pac.isUnnamed) name else pac.getName + "." + name

    Symbol.translate(fullyQualfiedName)
  }

  def canonicalType(binding: ITypeBinding) = if (binding.isArray()) binding.getElementType

  def isJavaLangType(binding: ITypeBinding): Boolean =
    Option(binding.getPackage).filter(_.getName == "java.lang").isDefined

  def isJavaLangType(binding: ITypeBinding, name: String): Boolean =
    isJavaLangType(binding) && binding.getName == name

  private def translatePrimitive(binding: ITypeBinding) = {
    assert(binding.isPrimitive)
    val name = binding.getName

    name match {
      case "boolean" => "bool"
      case "char" => "wchar"
      case _ => name
    }
  }

  private def translateJavaLangType(binding: ITypeBinding) = {
    assert(isJavaLangType(binding))
    val name = binding.getName

    name match {
      case "Class" => "ClassInfo"
      case _ => Symbol.translate(name)
    }
  }
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
