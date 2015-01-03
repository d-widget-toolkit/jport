package dwt.jport

object Symbol {
  def translate(str: String) = if (isDKeyword(str)) str + '_' else str
  def translate(str: Option[String]): Option[String] =
    if (str.isEmpty) None else Option(translate(str.get))

  def isDKeyword(str: String): Boolean = str match {
    case "abstract" |
      "alias" |
      "align" |
      "asm" |
      "assert" |
      "auto" |

      "body" |
      "bool" |
      "break" |
      "byte" |

      "case" |
      "cast" |
      "catch" |
      "cdouble" |
      "cent" |
      "cfloat" |
      "char" |
      "class" |
      "const" |
      "continue" |
      "creal" |

      "dchar" |
      "debug" |
      "default" |
      "delegate" |
      "delete" |
      "deprecated" |
      "do" |
      "double" |

      "else" |
      "enum" |
      "export" |
      "extern" |

      "false" |
      "final" |
      "finally" |
      "float" |
      "for" |
      "foreach" |
      "foreach_reverse" |
      "function" |

      "goto" |

      "idouble" |
      "if" |
      "ifloat" |
      "import" |
      "in" |
      "inout" |
      "int" |
      "interface" |
      "invariant" |
      "ireal" |
      "is" |

      "lazy" |
      "long" |

      "macro" |
      "mixin" |
      "module" |

      "new" |
      "nothrow" |
      "null" |

      "out" |
      "override" |

      "package" |
      "pragma" |
      "private" |
      "protected" |
      "public" |
      "pure" |

      "real" |
      "ref" |
      "return" |

      "scope" |
      "shared" |
      "short" |
      "static" |
      "struct" |
      "super" |
      "switch" |
      "synchronized" |

      "template" |
      "this" |
      "throw" |
      "true" |
      "try" |
      "typedef" |
      "typeid" |
      "typeof" |

      "ubyte" |
      "ucent" |
      "uint" |
      "ulong" |
      "union" |
      "unittest" |
      "ushort" |

      "version" |
      "void" |
      "volatile" |

      "wchar" |
      "while" |
      "with" |

      "__FILE__" |
      "__LINE__" |
      "__DATE__" |
      "__TIME__" |
      "__TIMESTAMP__" |
      "__VENDOR__" |
      "__VERSION__" |
      "immutable" |
      "nothrow" |
      "pure" |
      "shared" |

      "__gshared" |
      "__thread" |
      "__traits" |

      "__EOF__" => true

    case _ => str.nonEmpty && str(0) == '@'
  }
}
