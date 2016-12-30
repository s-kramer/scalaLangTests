package org.skramer.scalalang

/**
  * Created by skramer on 30.12.16.
  */

class ImportTests extends FlatSpecWithMatchers {
  "all members of a class" can "be imported" in {
    class Foo {
      def bar: Int = 5

      def baz: Int = 15
    }

    def checkFoo(f: Foo): Unit = {
      import f._
      bar shouldBe 5
      baz shouldBe 15
    }

    checkFoo(new Foo)
  }
}
