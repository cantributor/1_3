package ru.otus.c02_adt

import java.nio.file.Path

object adt3 {

  // Тип "сумма"

  // Смешать все Int и все Boolean
  sealed trait MyIntOrBoolean
  final case class MyInt(i: Int)         extends MyIntOrBoolean
  final case class MyBoolean(b: Boolean) extends MyIntOrBoolean

  // Сложить 2 любых множества
  type Sum[+A, +B] = Either[A, B]

  // Кортэж - наиболее общее представление "произведения" == наименее информативное
  // Either - *почти* наиболее общее представление "суммы" - right-biased с версии 2.12

  // type IntOrBoolean = ???

  // Значение или его отсутствие
  type IntOrNoInt = Either[Unit, Int]

  {
    (_: IntOrNoInt).map(_ + 1)
  }

  type TOrNoT[+T] = Either[Unit, T]

  // лучше - Option

  // Значение или бросок исключения
  // java:
  // int method() throws Throwable
  type TOrThrowable[+T] = Either[Throwable, T]

  // лучше - Try

  // Множество альтернатив
  case object FileNotFound
  case class FileParseError(description: String)
  case class InsufficientPrivileges(details: String)

  case class ParsedFile()

  def parseFile(
      path: Path
  ): Either[Either[FileNotFound.type, Either[FileParseError, InsufficientPrivileges]], ParsedFile] =
    ???

  // лучше - sealed trait
  // scala 3.0: ParseFileError = FileNotFound | FileParseError | InsufficientPrivileges
  // shapeless: FileNotFound :+: FileParseError :+: InsufficientPrivileges :+: CNil

}
