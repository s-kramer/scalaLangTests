package org.skramer.fpis.laziness

import org.skramer.scalalang.FlatSpecWithMatchers

/**
  *
  *
  * @author created: skramer on 19.05.18 14:45
  */
class StreamTest extends FlatSpecWithMatchers {
  private val EMPTY = Empty
  private val STREAM = Stream(1, 2, 3)
  private val LONG_STREAM = Stream(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

  private def infiniteStream: Stream[Int] = Stream.tabulate(0)(a => a)

  private val infiniteStreamTop5 = List(0, 1, 2, 3, 4)

  "empty stream" should "have no head" in {
    EMPTY.headOption shouldBe None
  }

  it should "not blow up when requesting more elements than available" in {
    EMPTY.take(3).toList shouldBe List()
  }

  it should "remain empty after dropping elements" in {
    EMPTY.drop(100).toList shouldBe List()
  }

  it should "remain empty on takeWhile" in {
    EMPTY.takeWhile(_ => true).toList shouldBe List()
  }

  "non-empty stream" should "have head" in {
    STREAM.headOption shouldBe Some(1)
  }

  "stream arguments" should "be evaluated lazily" in {
    def s: Stream[Int] = Stream.cons(1, s)

    s.headOption shouldBe Some(1)
  }

  "stream" should "be transformable to List" in {
    STREAM.toList shouldBe List(1, 2, 3)
  }

  it should "enable taking n elements" in {
    LONG_STREAM.take(3).toList shouldBe Stream(1, 2, 3).toList
  }

  it should "drop requested number of elements" in {
    STREAM.drop(2).toList shouldBe List(3)
  }

  it should "take elements until predicate evaluates to false" in {
    LONG_STREAM.takeWhile(_ <= 3).toList shouldBe List(1, 2, 3)
  }

  it should "be foldable" in {
    LONG_STREAM.foldRight("11") { case (e, acc) => e.toString + acc } shouldBe (1 to 11).mkString
  }

  "infinite stream" should "be creatable" in {
    val buffer = scala.collection.mutable.ListBuffer[Int]()

    def infiniteStream: Stream[Int] = Stream.tabulate(0)(a => {
      buffer.append(a)
      a
    })

    infiniteStream.take(5).toList shouldBe infiniteStreamTop5
    buffer.result() shouldBe infiniteStreamTop5
  }

  it should "not evaluate complete stream if unnecessary (recursive version)" in {
    infiniteStream.existsRec(_ > 5) shouldBe true
  }

  it should "not find element if it's not there" in {
    Stream.cons(1, EMPTY).existsRec(_ >= 2) shouldBe false
  }

  it should "emulate exists with foldRight" in {
    LONG_STREAM.exists(_ > 5) shouldBe true
  }
}
