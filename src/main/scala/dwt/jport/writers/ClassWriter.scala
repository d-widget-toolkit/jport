package dwt.jport.writers

object ClassWriter extends Writer
{
  def write (name: String, modifiers: String, superclass: String,
    interfaces: Seq[String], generics: Iterable[String],
    hasMembers: Boolean): Unit = {

    if (modifiers.nonEmpty)
      buffer.append(modifiers, ' ')

    buffer.append("class", ' ', name)

    if (generics.nonEmpty) {
      buffer.append(' ', '(')
      buffer.join(generics, ", ")
      buffer.append(')')
    }

    val bases: Seq[String] = if (superclass == null) interfaces
      else superclass +: interfaces

    if (!bases.isEmpty)
      buffer += " : "

    buffer.join(bases, ", ")

    if (hasMembers)
      buffer.append(nl)

    else
      buffer.append(' ')

    buffer.append('{')

    if (hasMembers)
      buffer.append(nl)

    buffer.append('}', nl, nl)
  }
}
