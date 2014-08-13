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

  def convert (modifiers: Iterable[IExtendedModifier]): String = {
    val mods = filterModifiers(modifiers)
    var keywords: Iterable[String] = mods.filterNot(_.isPublic).
      map(_.getKeyword.toString())

    if (isPackage(mods))
      keywords = Array("package") ++ keywords

    keywords.mkString(" ")
  }

  private def filterModifiers (modifiers: Iterable[IExtendedModifier]) =
    modifiers.filter(_.isModifier).asInstanceOf[Iterable[Modifier]].
    filter(e => validDModifiers.contains(e.getKeyword.toFlagValue()))

  private def isPackage (modifiers: Iterable[Modifier]): Boolean =
    modifiers.filterNot(e => e.isPublic && e.isProtected && e.isPrivate).isEmpty
}