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
        val zero: Int                = 0
        def sum(a: Int, b: Int): Int = a + b
      }

      intProduct(4, 3) shouldBe 12
      intProduct(4, 3)(IntMonoid) shouldBe 12
    }

    "complex monoid" in {
      case class Complex(re: Double, im: Double)

      implicit object ComplexMonoid extends Monoid[Complex] {
        val zero: Complex                        = Complex(0, 0)
        def sum(a: Complex, b: Complex): Complex = Complex(a.re + b.re, a.im + b.im)
      }

      // TODO
      intProduct(Complex(0.5, 1.5), 3) shouldBe Complex(1.5, 4.5)
    }
  }
}
