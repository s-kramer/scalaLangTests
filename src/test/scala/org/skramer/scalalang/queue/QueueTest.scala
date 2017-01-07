package org.skramer.scalalang.queue

import org.skramer.scalalang.FlatSpecWithMatchers

/**
  * Created by skramer on 07.01.17.
  */
class QueueTest extends FlatSpecWithMatchers {
  "newly created queue" should "have given number of elements" in {
    val q = new Queue(1, 2, 3)
    q.head shouldBe 1
    q.tail.head shouldBe 2
    q.tail.tail.head shouldBe 3
  }

  "new element" can "be enqueued in queue" in {
    val q = new Queue(1, 2, 3)
    val newQ = q.enqueue(4)

    newQ.head shouldBe 1
    newQ.tail.head shouldBe 2
    newQ.tail.tail.head shouldBe 3
    newQ.tail.tail.tail.head shouldBe 4
  }

  "initially empty queue" can "be extended with elements" in {
    val q = new Queue[Int]()
    // q.leading is now empty
    val newQ = q.enqueue(1).enqueue(2).enqueue(3)
    // newQ.leading is still empty

    newQ.head shouldBe 1 //this calls mirror and reverses the trailing list on every invocation
    newQ.tail.head shouldBe 2 // tail calls mirror, head is performed on an non-empty leading list
    newQ.tail.tail.head shouldBe 3 //first tail calls mirror, other operations don't need to
  }
}
