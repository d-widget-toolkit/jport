package dwt.jport

import scala.util.DynamicVariable

class Diagnostic {
  private var _hasDiagnostics = false

  def hasDiagnostics = _hasDiagnostics

  def error(filename: String, lineNumber: Int, message: String): Unit = {
    _hasDiagnostics = true
    System.err.println(s"$filename:$lineNumber: error: $message")
  }
}
