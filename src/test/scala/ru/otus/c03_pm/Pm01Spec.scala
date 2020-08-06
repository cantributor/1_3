package ru.otus.c03_pm

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class Pm01Spec extends AnyFreeSpec {

  "Сопоставление с образком" - {
    "всегда успешное сопоставление" in {
      val res = 0 match {
        case _ => 666
      }

      res should be(666)
    }

    "сопоставление с присвоением" in {
      val i = 1

      val res = "" match {
        case i =>
          val s: String = i
          s
      }

      res should be("")
    }

    "сопоставление с константной" in {
      val Const = 1
      val const = 2
      object a {
        val const = 3
      }

      val res = 4 match {
        case 0       => "literal"
        case Const   => "Const"
        case a.const => "a.const"
        case `const` => "`const`"
        case _       => "default"
      }

      res should be("default")
    }

    "объединение альтернативных паттернов" in {
      val res = 2 match {
        case 1 | 2 | 3 => "1,2,3"
        case _         => "default"
      }

      res should be("1,2,3")
    }

    "присвоение через @" in {
      val res = 2 match {
        case i @ (1 | 2 | 3) => s"1,2,3: $i"
        case i               => s"default: $i"
      }

      res should be("1,2,3: 2")
    }

    "уточнение типа" in {
      sealed trait MT
      case class A() extends MT
      case class B() extends MT

      val res = (A(): MT) match {
        case _: A => "a"
        case b: B => s"b: $b"
      }

      res should be("a")
    }

    "MatchError" in {
      sealed trait MT
      case class A() extends MT
      case class B() extends MT

      // compilation warning: match may not be exhaustive
      assertThrows[MatchError] {
        (A(): MT) match {
          case _: B =>
        }
      }
    }

    "match guards" in {
      val res = 13 match {
        case i if i % 2 == 0 => "even"
        case _ => "odd"
      }

      res should be("odd")
    }

    "сопоставление с кортежем" in {
      val pair: (Boolean, Int) = (true, 2)

      val res = pair match {
        case (true, i @ (1 | 2 | 3)) => s"success 1-3: $i"
        case (false, _)              => "failure"
      }

      res should be("success 1-3: 2")
    }

    "match guards отключают exhaustive check" in {
      val pair: (Boolean, Int) = (true, 2)

      val res = pair match {
        case (true, i) if i >= 1 && i <= 3 => s"success 1-3: $i"
        case (false, _)                    => "failure"
      }

      res should be("success 1-3: 2")
    }

    "unapply(...): Boolean" in {
      object IsEven {
        def unapply(i: Int): Boolean = i % 2 == 0
      }

      val res = 666 match {
        case IsEven() => "even"
        case _        => "odd"
      }

      res should be("even")
    }

    "unapply(...): Option[T]" in {
      object IsInt {
        def unapply(l: Long): Option[Int] =
          if (l >= Int.MinValue && l <= Int.MaxValue) Some(l.toInt)
          else None
      }

      val res = 666L match {
        case IsInt(i) => s"int: ${i}"
        case _        => "too big"
      }

      res should be("int: 666")
    }

    "unapply(...): Option[(A, B)]" in {
      case class Person(name: String, age: Int)

      val res1 = Person("Bob", 13) match {
        case Person(name, 13) => name
        case _                => "not 13"
      }

      res1 should be("Bob")

      // pattern matching for function parameters
      Some(Person("Bob", 13)).map {
        case Person(name, age) =>
          s"$name, $age"
      } should be(Some("Bob, 13"))
    }

    "unapplySeq(...): Option[Seq[T]]" in {
      val l = 1 :: 2 :: Nil

      l match {
        case head :: tail => println(s"$head :: $tail")
      }

      l match {
        case ::(head, tail) => println(s"$head :: $tail")
      }

      val s: Seq[Int] = l.toVector

      val res1 = s match {
        case Seq(a1)          => s"1 element $a1"
        case Seq(_, _)        => "2 elements"
        case Seq(_, _, _ @_*) => "3 elements or more"
      }

      res1 should be("2 elements")

      val reg = "a(b+)c(d+)e".r

      val res2 = "abcddde" match {
        case reg(b, d) => s"b: $b, d: $d"
        case _         => "failed"
      }

      res2 should be("b: b, d: ddd")
    }

    "присвоение нескольких переменных" in {
      val (a, b) = (1, "x")
      a should be(1)
      b should be("x")

      case class Person(name: String, age: Int)

      val Person(name, age) = Person("Bob", 13)

      name should be("Bob")
      age should be(13)

      assertThrows[MatchError] {
        val a: Any              = 1
        val (x: Int, _: String) = a
        println(x)
      }
    }

    "TODO: return name of adults only" in {
      case class Person(name: String, age: Int)

      def getNameOfReject(p: Person): String =
        p match {
          case _ => "rejected"
        }

      getNameOfReject(Person("John", 666)) should be("John")
      getNameOfReject(Person("Bob", 13)) should be("rejected")
    }
  }

}
