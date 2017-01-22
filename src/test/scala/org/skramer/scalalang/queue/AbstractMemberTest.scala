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

    assertDoesNotCompile(
      """
        | class Buzz extends Foo {
        |   override def bar: String = "qwerty"
        |}
      """.stripMargin)

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
}
