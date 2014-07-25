package dwt.jport.driver

import dwt.jport.DCoder

object JPort extends App
{
  Application.run(args)
  println(DCoder.dcoder)
  println("ok")
}