package dwt.jport.writers

import dwt.jport.DCoder

trait Writer
{
  protected val nl = DCoder.nl
  protected val buffer = DCoder.dcoder
}