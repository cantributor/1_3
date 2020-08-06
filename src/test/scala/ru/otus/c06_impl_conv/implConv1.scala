package ru.otus.c06_impl_conv

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class implConv1 extends AnyFreeSpec {

  trait Monoid[T] {
    def zero: T
    def sum(a: T, b: T): T
  }

  object Monoid {
    def apply[T](implicit m: Monoid[T]): Monoid[T] = m
  }

  case class Complex[T](re: T, im: T)

  implicit object IntMonoid extends Monoid[Int] {
    def zero: Int                = 0
    def sum(a: Int, b: Int): Int = a + b
  }

  implicit object DoubleMonoid extends Monoid[Double] {
    def zero: Double                      = 0
    def sum(a: Double, b: Double): Double = a + b
  }

  implicit def complexMonoid[T: Monoid]: Monoid[Complex[T]] =
    new Monoid[Complex[T]] {
      private val mt       = Monoid[T]
      def zero: Complex[T] = Complex(mt.zero, mt.zero)
      def sum(a: Complex[T], b: Complex[T]): Complex[T] =
        Complex(mt.sum(a.re, b.re), mt.sum(a.im, b.im))
    }

  implicit class MonoidSyntax[T: Monoid](val a: T) {
    def +(b: T): T = Monoid[T].sum(a, b)
  }

  "Complex Monoid" - {
    "int" in {
      val a = Complex(1, 2)
      val b = Complex(3, 4)

      (a + b) shouldBe Complex(4, 6)
    }

    "double" in {
      val a = Complex(1.5, 2.5)
      val b = Complex(3.5, 4.0)

      (a + b) shouldBe Complex(5, 6.5)
    }
  }

}
