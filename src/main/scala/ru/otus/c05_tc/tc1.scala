package ru.otus.c05_tc

object tc1 {
  // Parse JSON
  // def ???

  trait Parser[T] {
    def parse(json: String): Option[T]
  }

  def parseJson[T](json: String)(implicit parser: Parser[T]): Option[T] =
    parser.parse(json)
}
