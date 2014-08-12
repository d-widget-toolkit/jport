name := "jport"

version := "0.0.1"

scalaVersion := "2.11.2"

unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil

unmanagedSourceDirectories in Test := (scalaSource in Test).value :: Nil

EclipseKeys.withSource := true

libraryDependencies ++= Seq(
  "org.eclipse.birt.runtime" % "org.eclipse.core.resources" % "3.9.0.v20140514-1307",
  "org.eclipse.birt.runtime" % "org.eclipse.osgi" % "3.10.0.v20140606-1445",
  "org.eclipse.core" % "contenttype" % "3.4.200-v20140207-1251",
  "org.eclipse.core" % "jobs" % "3.6.0-v20140424-0053",
  "org.eclipse.core" % "runtime" % "3.10.0-v20140318-2214",
  "org.eclipse.equinox" % "common" % "3.6.200-v20130402-1505",
  "org.eclipse.equinox" % "preferences" % "3.5.200-v20140224-1527",
  "org.eclipse.tycho" % "org.eclipse.jdt.core" % "3.10.0.v20140604-1726",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % Test
)
