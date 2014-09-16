package functional.support

import scala.collection.mutable.HashSet
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.scalatest.Matchers
import scalax.file.Path
import dwt.jport.JPorter
import dwt.jport.core.JPortAny.anyToJPortAny
import org.scalatest.matchers.Matcher
import support.Code

class Suite extends FunSuite with Matchers with BeforeAndAfter with Code {
  private val createdFiles = new HashSet[Path]
  var basePath: Path = null

  before {
    basePath = Path.createTempDirectory()
  }

  after {
    createdFiles.foreach(_.deleteIfExists())
    createdFiles.clear()
  }

  def portTo(expected: String) = be(expected).
    compose((java: String) => JPorter.port(java))

  /**
   * Custom matcher for comparing the ported Java code to the exected D code.
   *
   * @param filename the full path, excluding file extension, where to write the
   * 	original Java code
   *
   * @param expected the expected D code
   */
  def portFromFileTo(filename: String, expected: String): Matcher[String] =
    portFromFileTo(basePath / filename, expected)

  /**
   * Custom matcher for comparing the ported Java code to the exected D code.
   *
   * @param path the full path, excluding file extension, where to write the
   * 	original Java code
   *
   * @param expected the expected D code
   */
  def portFromFileTo(path: Path, expected: String) = be(expected).
    compose { (java: String) =>
      val p = Path.fromString(path.path + ".java").tap(_.write(java))
      JPorter.portFromFile(p.path, sourcepathEntries = Array(basePath.path))
    }

  /**
   * Custom matcher for comparing the ported Java code to the exected D code.
   *
   * @param path the full path, excluding file extension, where to write the
   * 	original Java code
   *
   * @param expected the expected D code
   */
  def portFromFileTo(expected: String) = be(expected).
    compose { (java: String) =>
      val path = Path.createTempFile(dir = basePath.path, suffix = ".java")
      path.write(java)

      JPorter.portFromFile(path.path, sourcepathEntries = Array(basePath.path))
    }

  /**
   * Creates a new file with the given filename and the given contents.
   *
   * @param filename the name of the file to create
   * @param code the code/contents of the file
   *
   * @return the directory path to where the file was saved
   */
  def codeToFile(filename: String)(code: String): Path = {
    val path = basePath / Path.fromString(s"$filename.java")
    path.write(trimCode(code))
    createdFiles += path

    val segments = path.segments
    val dir = segments.slice(1, segments.length - 1).mkString(path.separator)
    Path.fromString(path.separator + dir)
  }
}
