package ru.otus.c02_adt

object adt2 {

  // Тип "сумма"

  // Смешать все Int и все Boolean
  // MyIntOrBoolean

  sealed trait MyIntOrBoolean
  final case class MyInt(i: Int)         extends MyIntOrBoolean
  final case class MyBoolean(b: Boolean) extends MyIntOrBoolean

}
