package ru.otus.c01_variance

object var1 {
  trait Parent {
    def parentMethod(): Unit = println("parent method")
  }

  trait Child extends Parent {
    def childMethod(): Unit = println("child method")
  }

  trait Producer[+T] {
    def produce(): T
  }

  val childProducer: Producer[Child] = () => new Child {}
  val producedParent: Parent         = childProducer.produce()
//  val parentProducer: Producer[Parent] = childProducer

  trait Consumer[-T] {
    def consume(t: T): Unit
  }

  val child: Child                     = new Child {}
  val parentConsumer: Consumer[Parent] = p => println(p.parentMethod())
  parentConsumer.consume(child)
  val childConsumer: Consumer[Child] = parentConsumer

  trait Mapper[T] {
    def map(t: T): T
  }

  val parentToParent: Mapper[Parent] = p => new Parent {}
  val childToChild: Mapper[Child] = c => {
    c.childMethod()
    c
  }

  val mappedParent1: Parent = childToChild.map(child)
  val mappedParent2: Parent = parentToParent.map(child)

  val anyToNothing: Any => Nothing = _ => ???
  val nothingToAny: Nothing => Any = _ => ()

  val fx: String => Array[Int] = anyToNothing

  // covariant: M[+T]
  // val mp: M[Parent] = (???: M[Child])
  // val mc: M[Child] = (???: M[Parent]) // error

  // contravariant: M[-T]
  // val mp: M[Parent] = (???: M[Child]) // error
  // val mc: M[Child] = (???: M[Parent])

  // invariant: M[T]
  // val mc: M[Child] = (???: M[Parent]) // error
  // val mp: M[Parent] = (???: M[Child]) // error
}
