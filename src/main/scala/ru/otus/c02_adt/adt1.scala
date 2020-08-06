package ru.otus.c02_adt

/**
  * Product - тип "произведение"
  *
  * Представляем любой тип как множество всех возможных значений.
  */
object adt1 {

  // Boolean - множество из 2 элементов
  val b1: Boolean = true
  val b2: Boolean = false

  // Byte - множество из ??? элементов
  val bite1: Byte = 1
  val bite2: Byte = 127
  val bite3: Byte = -128

  // Int - множество из ??? элементов
  val i1 = Int.MinValue
  val i2 = 1
  val i3 = Int.MaxValue

  // Unit - множество из одного элемента

  // Кортезиян - декартово произведение множеств - ???

  // Кортеж
  type IntProdBoolean = (Int, Boolean)
  case class CCIntProdBoolean(i: Int, b: Boolean)

  val c2cc: IntProdBoolean => CCIntProdBoolean = CCIntProdBoolean.tupled
  val cc2c: CCIntProdBoolean => IntProdBoolean = { case CCIntProdBoolean(i, b) => (i, b) }

//  CCIntProdBoolean(1, false).productArity

}
