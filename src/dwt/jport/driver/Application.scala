package dwt.jport.driver

import dwt.jport.JPorter

object Application
{
  private var args: Array[String] = null

  def run (args: Array[String]): Unit = {
    //val name = "/Users/jacob/Downloads/swt-4.4-cocoa-macosx-x86_64/src/org/eclipse/swt/accessibility/Relation.java"
    val name = "/Users/jacob/development/java/Bar.java"
    //val name = "~/development/java/Relation.java"
    new JPorter(name).port()
  }
}