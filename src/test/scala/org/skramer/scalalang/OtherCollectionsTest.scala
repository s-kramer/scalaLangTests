package org.skramer.scalalang

import scala.collection.immutable.TreeSet
import scala.collection.mutable.{Set => MSet, TreeSet => MTreeSet}

/**
  * Created by skramer on 02.01.17.
  */
@SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
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

  "tree set" should "keep the elements ordered" in {
    val s = MTreeSet.empty[Int]
    s ++= List(5, 4, 3, 2, 1)
    s += 1
    s += 2
    s += 3
    s += 4

    s -= 3
    s --= List(3, 4)

    s should contain inOrder(1, 2, 5)
  }

  "immutable collection" can "be extended with new elements with +=" in {
    var s = TreeSet(1, 2, 3) // var not val required to reassign the modified collection later
    s should contain allOf(1, 2, 3)
    s += 4
    s should contain allOf(1, 2, 3, 4)
  }

  "any kind of value" can "be reassigned using +=" in {
    var f = 1F / 3F
    assert(f === 0.333F +- 0.001F)

    f += 1F / 3F
    assert(f === 0.666F +- 0.001F)
  }

  "immutable collection" can "be initialized with another collection" in {
    val s = TreeSet.empty[Int] ++ List(4, 3, 2, 1)
    s should contain inOrderOnly(1, 2, 3, 4)
  }

}
