package dwt.jport.analyzers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.IExtendedModifier
import org.eclipse.jdt.core.dom.Modifier

object Modifiers
{
  private val validDModifiers = Set(
    Modifier.ABSTRACT,
    Modifier.FINAL,
    Modifier.PRIVATE,
    Modifier.PROTECTED,
    Modifier.PUBLIC,
    Modifier.STATIC,
    Modifier.SYNCHRONIZED,
    Modifier.VOLATILE
  )

  def convert (modifiers: Iterable[IExtendedModifier], virtual: Boolean = false): String = {
    val mods = filterModifiers(modifiers)
    val accessModifier = getAccessModifier(mods, virtual)
    val rest = mods.filterNot(isAccessModifier)
    val keywords = Array(accessModifier) ++ rest.map(_.getKeyword.toString())
    keywords.filter(_.nonEmpty).mkString(" ")
  }

  private def filterModifiers (modifiers: Iterable[IExtendedModifier]) =
    modifiers.filter(_.isModifier).asInstanceOf[Iterable[Modifier]].
    filter(e => validDModifiers.contains(e.getKeyword.toFlagValue()))

  private def getAccessModifier (modifiers: Iterable[Modifier], virtual: Boolean): String = {
    val mods = modifiers.filter(isAccessModifier)

    if (mods.isEmpty) {
      return if (virtual) "/* package */" else "package"
    }

    val mod = mods.head

    if (mod.isPublic) "" else mod.getKeyword.toString()
  }

  private def isAccessModifier (m: Modifier) =
    m.isPrivate || m.isProtected || m.isPublic
}