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

    toString +=: buffer
  }

  lazy val importsWPackage = uniqueImports.filter(_.contains('.')).
    filterNot(_ == "java.lang.Object").sortWith(_ < _)

  lazy val importsWOPackage = uniqueImports.filter(!_.contains('.')).
    sortWith(_ < _)

  override def toString() = {
    val groups = importsWPackage.groupBy(e => e.split('.').head).values.toArray
    val sortedGroups = groups.sortWith(sort) ++ Array(importsWOPackage)
    val imports = sortedGroups.filter(_.nonEmpty).
      map(e => e.map(toImportString).mkString("\n"))

    imports.mkString("\n\n") + "\n\n"
  }

  private lazy val uniqueImports = imports.distinct
  private def toImportString(s: String) = s"import $s;"
  private def sort(a: StringArray, b: StringArray) =
    if (a.nonEmpty && b.nonEmpty) a.head < b.head else false
}
