package org.skramer.scalalang.queue

import org.skramer.scalalang.FlatSpecWithMatchers

/**
  * Created by skramer on 15.01.17.
  */
class AbstractMemberTest extends FlatSpecWithMatchers {
  "abstract var" can "only be overridden by var" in {
    abstract class Foo {
      var bar: String
    }

    class Bar(override var bar: String) extends Foo
    //      class Buzz(override val bar: String) extends Foo // this won't compile as the setter is not overridden
    //      class Qux extends Foo {
    //        override def bar: String = "Qux" // the same issue
    //      }
  }

  "abstract val" can "only be overridden by val" in {
    abstract class Foo {
      val bar: String
    }

    // this doesn't compile but assertDoesNotCompile doesn't seem to detect that :(
    //    class Buzz extends Foo {
    //      override def bar: String = "qwerty"
    //    }

    class Bar(override val bar: String) extends Foo
  }

  "abstract method" can "be overridden with val or with other method" in {
    abstract class Foo {
      def bar: String
    }

    class Buz extends Foo {
      override def bar: String = "Concrete method"
    }

    class Qux extends Foo {
      override val bar: String = "concrete val"
    }
  }

  trait Foo {
    require(bar != 0)
    val bar: Int
  }

  val multiplier = 2

  class Buz(override val bar: Int) extends Foo {
  }

  "class parameters" should "be initialized before it's passed to the constructor" in {
    val b = new Buz(5 * multiplier) // this doesn't throw as the abstract field is initialized during class creation, before the superconstructor is called
    assert(b.bar === 10)
  }

  "implementing val" should "be initialized after calling superconstructor" in {
    assertThrows[IllegalArgumentException](new Foo {
      override val bar: Int = 8 * multiplier // this throws because the superclass constructor with the require clause gets called before this value is initialized
    })
  }
}
