package org.skramer.scalalang

/**
  * Created by skramer on 21.12.16.
  */
class FunctionalObjectTest extends FlatSpecWithMatchers {
  "constructor params of other objects" should "not be accessible from this object" in {
    class Foo(i: Int) {
      def add(f: Foo): Int = {
        assertDoesNotCompile(
          """
            |i + f.i
          """.stripMargin)
        0
      }
    }
  }

  "fields of other objects" should "be accessible from this object" in {
    class Foo(val i: Int) {
      def add(f: Foo): Int = {
        i + f.i
      }
    }
  }
}


