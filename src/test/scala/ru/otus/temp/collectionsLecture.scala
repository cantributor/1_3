package ru.otus.temp

import java.util.function.Predicate

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class collectionsLecture extends AnyFreeSpec {
  "examples" - {
    "square" in {
      def squareList(xs: List[Int]): List[Int] =
        xs match {
          case Nil     => Nil
          case y :: ys => y * y :: squareList(ys)
        }

      squareList(1 :: 2 :: 3 :: Nil) shouldBe (1 :: 4 :: 9 :: Nil)
    }

    "filter" in {
      def filterFM(xs: List[Int], pred: Int => Boolean): List[Int] =
        xs.flatMap(x => if (pred(x)) List(x) else Nil)

      filterFM(List(1, 2, 3, 4), x => (x % 2) == 0) shouldBe List(2, 4)
    }

    "custom for comprehension" in {
      sealed trait MaybeInt {
        def map(f: Int => Int): MaybeInt
        def flatMap(f: Int => MaybeInt): MaybeInt
      }

      case class SomeInt(i: Int) extends MaybeInt {
        def map(f: Int => Int): MaybeInt = {
          val fcall = f(i)
          println(s"map on $i called with result $fcall")
          SomeInt(fcall)
        }
        def flatMap(f: Int => MaybeInt): MaybeInt = {
          val fcall = f(i)
          println(s"flatMap on $i called with result $fcall")
          fcall
        }
      }

      val maybe1 = SomeInt(1)
      val maybe2 = SomeInt(2)

      val result = for {
        a <- maybe1
        b <- maybe2
      } yield a + b

//      val result: MaybeInt = maybe1
//        .flatMap((a: Int) =>
//          maybe2
//            .map((b: Int) => a.+(b))
//        )

      result.shouldBe(SomeInt(3))
    }
  }
}
