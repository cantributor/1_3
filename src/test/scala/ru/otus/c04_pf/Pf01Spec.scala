package ru.otus.c04_pf

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class Pf01Spec extends AnyFreeSpec {
  sealed trait Subject
  case class Person(name: String, age: Int) extends Subject
  case class Robot(id: Long)                extends Subject

  "total function" - {
    "with match" in {
      val f: Subject => String = p =>
        p match {
          case Person(name, age) => s"human named $name of age $age"
          case Robot(id)         => s"Kill all humans my fellow $id"
        }

      f(Person("Bob", 13)) should be("human named Bob of age 13")
    }

    "without match" in {
      val f: Subject => String = {
        case Person(name, age) => s"human named $name of age $age"
        case Robot(id)         => s"Kill all humans my fellow $id"
      }

      f(Person("Bob", 13)) should be("human named Bob of age 13")
    }

    "on option" in {
      Some(Robot(666): Subject).map {
        case Person(name, age) => s"human named $name of age $age"
        case Robot(id)         => s"Kill all humans my fellow $id"
      } should be(Some("Kill all humans my fellow 666"))
    }
  }

  "partial function" - {
    "extends function" in {
      val robotId: PartialFunction[Subject, Long] = {
        case Robot(id) => id
      }

      val f: Subject => Long = robotId

      assertThrows[MatchError](f(Person("", 0)))

      robotId.isDefinedAt(Person("", 0)) shouldBe false
      robotId.isDefinedAt(Robot(0)) shouldBe true

      val lifted: Subject => Option[Long] = robotId.lift

      lifted(Person("", 0)) shouldBe None
      lifted(Robot(666)) shouldBe Some(666)
    }

    "on option" in {
      val optSubj: Option[Subject] = Some(Robot(666))

      val optRobotId: Option[Long] = optSubj.collect {
        case Robot(id) => id
      }

      optRobotId shouldBe Some(666)
    }

    "on try" in {
      class MyException1 extends RuntimeException
      class MyException2 extends RuntimeException

      def m1: Int = throw new MyException1
      def m2: Int = throw new MyException2

      val res1 =
        try m1
        catch {
          case _: MyException1 => 1
          case _: MyException2 => 2
        }

      res1 shouldBe 1

      val handler: PartialFunction[Throwable, Int] = {
        case _: MyException1 => 1
        case _: MyException2 => 2
      }

      val res2 =
        try m2
        catch handler

      res2 shouldBe 2
    }
  }
}
