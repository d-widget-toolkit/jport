package dwt.jport.writers

import dwt.jport.DCoder
import dwt.jport.ast.AstNode

trait Writer[T] {
  protected val nl = DCoder.nl
  protected val buffer = DCoder.dcoder

  protected var node: T = _
  protected var importWriter: ImportWriter = null
}
