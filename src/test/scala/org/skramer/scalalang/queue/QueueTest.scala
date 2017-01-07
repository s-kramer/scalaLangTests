package org.skramer.scalalang.queue

import org.skramer.scalalang.FlatSpecWithMatchers

/**
  * Created by skramer on 07.01.17.
  */
class QueueTest extends FlatSpecWithMatchers {
  "newly created queue" should "have given number of elements" in {
    val q = new Queue[Int](List(1, 2, 3))
    q.head shouldBe 1
    q.tail.head shouldBe 2
    q.tail.tail.head shouldBe 3
  }

  "new element" can "be enqueued in queue" in {
    val q = new Queue(List(1, 2, 3))
    val newQ = q.enqueue(4)

    newQ.head shouldBe 1
    newQ.tail.head shouldBe 2
    newQ.tail.tail.head shouldBe 3
    newQ.tail.tail.tail.head shouldBe 4
  }
}
