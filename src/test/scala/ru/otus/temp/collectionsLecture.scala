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
  }
}
