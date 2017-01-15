package org.skramer.scalalang.queue

import org.skramer.scalalang.FlatSpecWithMatchers

/**
  * Created by skramer on 07.01.17.
  */
class QueueTest extends FlatSpecWithMatchers {
  "newly created queue" should "have given number of elements" in {
    val q = Queue(1, 2, 3)
    q.head shouldBe 1
    q.tail.head shouldBe 2
    q.tail.tail.head shouldBe 3
  }

  "new element" can "be enqueued in queue" in {
    val q = Queue(1, 2, 3)
    val newQ = q.enqueue(4)

    newQ.head shouldBe 1
    newQ.tail.head shouldBe 2
    newQ.tail.tail.head shouldBe 3
    newQ.tail.tail.tail.head shouldBe 4
  }

  "initially empty queue" can "be extended with elements" in {
    val q = Queue[Int]()
    // q.leading is now empty
    val newQ = q.enqueue(1).enqueue(2).enqueue(3)
    // newQ.leading is still empty

    newQ.head shouldBe 1 //this calls mirror and reverses the trailing list on every invocation
    newQ.tail.head shouldBe 2 // tail calls mirror, head is performed on an non-empty leading list
    newQ.tail.tail.head shouldBe 3 //first tail calls mirror, other operations don't need to
  }

  "queue with lower bound" can "make queues of super type" in {
    class Foo
    class Bar extends Foo
    class Buzz extends Foo
    val queueOfBuzz: CovariantQueue[Buzz] = Queue.newCovariantQueue(new Buzz, new Buzz)
    val queueOfFoo: CovariantQueue[Foo] = queueOfBuzz.enqueue(new Bar) // result is a Queue of supertype

    queueOfFoo.head shouldBe a[Buzz]
    queueOfFoo.tail.head shouldBe a[Buzz]
    queueOfFoo.tail.tail.head shouldBe a[Bar]
  }

  "queue with mutating mirror" can "still be pure but can avoid frequent reverse cost of pure mirror" in {
    val q = Queue.newQueueWithMutatingMirror(1, 2)
    val threeElementQueue = q.enqueue(3)
    val fourElementQueue = threeElementQueue.enqueue(4)

    fourElementQueue.head shouldBe 1 // uses non-empty leading
    fourElementQueue.tail.head shouldBe 2 // still uses non-empty leading
    fourElementQueue.tail.tail.head shouldBe 3 // head performs the only mirror operation - reverses the trailing and concatenates the lists
    fourElementQueue.tail.tail.tail.head shouldBe 4 // mirror operation is not required for head as it was already performed and saved in the third tail call
  }

  "classes with upper bound Ordered" can "be compared" in {
    def isLessThan[T <: Ordered[T]](lhs: T, rhs: T): Boolean = {
      lhs < rhs
    }

    case class Person(name: String, surname: String) extends Ordered[Person] {
      override def compare(that: Person): Int = {
        name.compareToIgnoreCase(that.name) match {
          case 0 => 0
          case _ => surname.compareToIgnoreCase(that.surname)
        }
      }
    }

    val person1: Person = Person("qwe", "rty")
    val person2: Person = Person("abc", "def")
    val person3: Person = Person("bcd", "efg")
    person1 should be > person2
    isLessThan(person2, person3) shouldBe true
  }
}
