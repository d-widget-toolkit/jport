package dwt.jport.writers

class ImportWriter extends Buffer {
  private type StringArray = scala.collection.mutable.ArrayBuffer[String]
  private var imports = new StringArray

  def :+(imports: Array[String]) = this.imports ++= imports
  def :+(imports: String) = this.imports += imports

  def write(): Unit = {
    if (this.imports.isEmpty) return

    val groups = importsWPackage.groupBy(e => e.split('.').head).values.toArray
    val sortedGroups = groups.sortWith(sort) ++ Array(importsWOPackage)
    val imports = sortedGroups.map(e => e.map(toImportString).mkString("\n"))
    val str = imports.mkString("\n\n") + "\n\n"
    str +=: buffer
  }

  def importsWPackage = this.imports.filter(_.contains('.')).sortWith(_ < _)
  def importsWOPackage = this.imports.filter(!_.contains('.')).sortWith(_ < _)

  private def toImportString(s: String) = s"import $s;"
  private def sort(a: StringArray, b: StringArray) =
    if (a.nonEmpty && b.nonEmpty) a.head < b.head else false
}
