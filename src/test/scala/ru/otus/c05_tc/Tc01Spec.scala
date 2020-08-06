package ru.otus.c05_tc

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class Tc01Spec extends AnyFreeSpec {
  "Monoid" - {
    trait Monoid[T] {
      def zero: T
      def sum(a: T, b: T): T
    }

    object Monoid {
      def apply[T](implicit m: Monoid[T]): Monoid[T] = m
    }

    def intProduct[T: Monoid](t: T, i: Int): T = {
      def inner(acc: T, i: Int): T =
        if (i < 1) acc
        else inner(Monoid[T].sum(acc, t), i - 1)

      inner(Monoid[T].zero, i)
    }

    "int monoid" in {
      implicit object IntMonoid extends Monoid[Int] {
        def zero: Int                = 0
        def sum(a: Int, b: Int): Int = a + b
      }

      intProduct(4, 3) shouldBe 12
    }

    "complex monoid" in {
      case class ComplexInt(re: Int, im: Int)

      // TODO
      // intProduct(ComplexInt(4, 3), 3) shouldBe ComplexInt(12, 9)
    }
  }
}
