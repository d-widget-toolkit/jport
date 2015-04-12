package dwt.jport.core

import java.util.{ List => JavaList }

class TypedList(val list: JavaList[_]) {
  def typed[T] = list.asInstanceOf[JavaList[T]]
}

object JavaListImplicits {
  implicit def JavaListToTypedList(list: JavaList[_]) =
    new TypedList(list)
}
