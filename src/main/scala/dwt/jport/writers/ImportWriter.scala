package dwt.jport.writers

class ImportWriter extends Buffer {
  private type StringArray = scala.collection.mutable.ArrayBuffer[String]
  private var imports = new StringArray

  def :+(imports: Iterable[String]) = this.imports ++= imports
  def :+(imports: String) = this.imports += imports

  def +=(imports: Iterable[String]) = this.imports ++= imports
  def +=(imports: String) = this.imports += imports

  def write(): Unit = {
    if (importsWPackage.isEmpty && importsWOPackage.isEmpty) return

    val groups = importsWPackage.groupBy(e => e.split('.').head).values.toArray
    val sortedGroups = groups.sortWith(sort) ++ Array(importsWOPackage)
    val imports = sortedGroups.map(e => e.map(toImportString).mkString("\n"))
    val str = imports.mkString("\n\n") + "\n\n"
    str +=: buffer
  }

  lazy val importsWPackage = uniqueImports.filter(_.contains('.')).
    filterNot(_ == "java.lang.Object").sortWith(_ < _)

  lazy val importsWOPackage = uniqueImports.filter(!_.contains('.')).
    sortWith(_ < _)

  private lazy val uniqueImports = imports.distinct
  private def toImportString(s: String) = s"import $s;"
  private def sort(a: StringArray, b: StringArray) =
    if (a.nonEmpty && b.nonEmpty) a.head < b.head else false
}
