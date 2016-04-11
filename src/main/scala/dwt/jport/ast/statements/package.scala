package dwt.jport.ast

import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }

package object statements {
  type Statement = TypedStatement[JdtStatement]
}
