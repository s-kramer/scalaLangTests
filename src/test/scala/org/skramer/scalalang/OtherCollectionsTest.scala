package org.skramer.scalalang

import scala.collection.mutable.{Set => MSet, TreeSet => MTreeSet}

/**
  * Created by skramer on 02.01.17.
  */
class OtherCollectionsTest extends FlatSpecWithMatchers {
  "list buffer" can "create a list by appending and prepending in constant time" in {
    val buf = new collection.mutable.ListBuffer[Int]
    buf += 1
    buf += 2
    3 +=: buf
    buf.append(4)
    buf.prepend(5)
    buf.toList shouldBe List(5, 3, 1, 2, 4)
  }

  "string" can "be viewed as collection thanks to implicit conversion to stringOps" in {
    val qwerty = "qwerty"
    qwerty.contains('q') shouldBe true
    (qwerty, List.range(0, qwerty.length)).zipped.toList shouldBe qwerty.zipWithIndex
  }

  "mutable set" can "be expanded with elements" in {
    val s = MSet.empty[Int]
    s ++= List(1, 2, 3, 4, 5)
    s += 1
    s += 2
    s += 3
    s += 4

    s -= 3
    s --= List(3, 4)

    s should be(MSet(1, 2, 5))

    s & Set(1, 2, 7) shouldBe Set(1, 2)
  }

  "tree set" should "retain the order of elements" in {
    val s = MTreeSet.empty[Int]
    s ++= List(1, 2, 3, 4, 5)
    s += 1
    s += 2
    s += 3
    s += 4

    s -= 3
    s --= List(3, 4)

    s should contain inOrder(1, 2, 5)
  }

}
