package dwt.jport.driver

import dwt.jport.JPorter

object Application {
  private var args: Array[String] = null

  def run(args: Array[String]): Unit = {
    val filename = "/Users/doob/Downloads/swt-4.5-cocoa-macosx-x86_64/src/org/eclipse/swt/accessibility/Relation.java"
    //val filename = "/Users/jacob/development/java/Bar.java"
    //val filename = "~/development/java/Relation.java"
    val result = JPorter.portFromFile(filename)
    println(result)
    println("ok")
  }
}
