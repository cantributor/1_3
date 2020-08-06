package ru.otus.c02_adt

import java.nio.file.Path

import scala.util.Try

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

  type IntOrBoolean = Either[Int, Boolean]

  // Значение или его отсутствие
  type IntOrNoInt = Either[Unit, Int]

  {
    (_: IntOrNoInt).map(_ + 1)
  }

  type TOrNoT[+T] = Either[Unit, T]

  // лучше - Option
  val x: Option[Int] = if (true) Some(1) else None

  // Значение или бросок исключения
  // java:
  // int method() throws Throwable
  type TOrThrowable[+T] = Either[Throwable, T]

  def method(): TOrThrowable[Int] = ???

  val xt: Try[Int] = Try {
    1 / 0
  }

  // лучше - Try

  // Множество альтернатив
  sealed trait ParseFileError
  case object FileNotFound                           extends ParseFileError
  case class FileParseError(description: String)     extends ParseFileError
  case class InsufficientPrivileges(details: String) extends ParseFileError

  case class ParsedFile()

  def parseFile(
      path: Path
  ): Either[ParseFileError, ParsedFile] =
    ???

  // лучше - sealed trait
  // scala 3.0: type ParseFileError = FileNotFound | FileParseError | InsufficientPrivileges
  // shapeless: FileNotFound :+: FileParseError :+: InsufficientPrivileges :+: CNil

}
