package dwt.jport.writers

class ImportWriter extends Writer[AnyRef] {
  private var imports = new scala.collection.mutable.ArrayBuffer[String]

  def :+(imports: Array[String]) = this.imports ++= imports
  def :+(imports: String) = this.imports += imports

  def write() = {
    val groups = this.imports.groupBy(e => e.split('.').head).values.toArray
    val sortedGroups = groups.sortWith(_.head < _.head)
    val imports = sortedGroups.map(e => e.map(toImportString).mkString("\n"))
    val str = imports.mkString("\n\n") + "\n\n"
    str +=: buffer
  }

  private def toImportString(s: String) = s"import $s;"
}