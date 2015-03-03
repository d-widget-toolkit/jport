package dwt.jport.writers

import dwt.jport.DCoder

object ModifierWriter {
  def write(buffer: DCoder, modifiers: String) =
    if (modifiers.nonEmpty)
      buffer.append(modifiers, ' ')
}
