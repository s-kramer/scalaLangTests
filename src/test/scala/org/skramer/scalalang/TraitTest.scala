package org.skramer.scalalang

import java.awt.Point

import org.skramer.scalalang.rational.Rational

import scala.collection.mutable.ArrayBuffer

/**
  * Created by skramer on 28.12.16.
  */
trait Hello {
  val MyMagicNumber: Int = Hello.MyMagicNumber
}

// TODO: suppress findbugs false positives
object Hello {
  val MyMagicNumber: Int = 5
}

class TraitTest extends FlatSpecWithMatchers {
  "trait" can "be mixed into class" in {

    val h = new Hello {}
    class Empty extends Hello
    // TODO: move magic number definition to class that mixes in the trait
    (new Empty).MyMagicNumber shouldBe h.MyMagicNumber
  }

  "trait" can "be mixed in during object creation" in {
    class Empty
    val e = new Empty with Hello
    e.MyMagicNumber shouldBe Hello.MyMagicNumber
  }

  "class with mixin" can "override the method from the trait" in {
    class Empty extends Hello {
      // note: val must be used here to override, you cannot override a val with a def
      override val MyMagicNumber: Int = Empty.NewMagicNumber
    }

    object Empty {
      final val NewMagicNumber: Int = 15
    }

    val e = new Empty
    e.MyMagicNumber shouldBe Empty.NewMagicNumber
  }

  "trait" can "define rich API in terms of few abstract methods" in {
    trait Rectangular {
      val bottomLeft: Point
      val topRight: Point

      def width: Int = topRight.x - bottomLeft.x

      def height: Int = topRight.y - bottomLeft.y

      def area: Int = width * height
    }

    class Rectangle(val bottomLeft: Point, val topRight: Point) extends Rectangular
    class Square(val bottomLeft: Point, val sideLength: Int) extends Rectangular {
      override val topRight: Point = new Point(bottomLeft.x + sideLength, bottomLeft.y + sideLength)
    }

    val r = new Rectangle(new Point(5, 5), new Point(12, 12))
    r.width shouldBe 7
    r.height shouldBe 7
    r.area shouldBe 49

    val s = new Square(new Point(5, 5), 5)
    s.width shouldBe 5
    s.height shouldBe 5
    s.area shouldBe 25
  }

  "ordered trait" should "influence sorting" in {
    class Foo(val value: Int) extends Ordered[Foo] {
      override def compare(that: Foo): Int = value - that.value
    }

    val f = new Foo(5)
    val k = new Foo(15)
    assert(f < k)
    assert(f <= k)
    assert(!(f > k))
    assert(!(f >= k))
  }

  "ordered rational" should "compare using fractional rules" in {
    val first, second = new Rational(1, 2) with Ordered[Rational] {
      override def compare(that: Rational): Int = nominator * that.denominator - that.nominator * denominator
    }

    assert(first >= second)
    assert(first <= second)
    assert(!(first > second))
    assert(!(first < second))
  }

  "stackable modifications" should "be invoked in correct order" in {
    class IntQueue {
      val queue = new ArrayBuffer[Int]()

      def put(value: Int): Unit = queue.append(value)

      def get: List[Int] = queue.toList
    }

    trait Doubling extends IntQueue {
      override def put(value: Int): Unit = super.put(2 * value)
    }

    trait Incrementing extends IntQueue {
      override def put(value: Int): Unit = super.put(value + 1)
    }

    trait Filtering extends IntQueue {
      override def put(value: Int): Unit = if (value >= 0) super.put(value)
    }

    val filteringIncrementingDoublingQueue = new IntQueue with Doubling with Incrementing with Filtering
    filteringIncrementingDoublingQueue.put(-1)
    filteringIncrementingDoublingQueue.put(0)
    filteringIncrementingDoublingQueue.put(1)

    filteringIncrementingDoublingQueue.get shouldBe List(2, 4)

    val doublingIncrementingFilteringQueue = new IntQueue with Filtering with Incrementing with Doubling
    doublingIncrementingFilteringQueue.put(-1)
    doublingIncrementingFilteringQueue.put(0)
    doublingIncrementingFilteringQueue.put(1)

    doublingIncrementingFilteringQueue.get shouldBe List(1, 3)
  }

}
