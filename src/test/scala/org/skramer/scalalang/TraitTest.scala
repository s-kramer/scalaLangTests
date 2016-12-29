package org.skramer.scalalang

import java.awt.Point

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

}
