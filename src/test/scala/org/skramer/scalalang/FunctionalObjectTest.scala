package org.skramer.scalalang

/**
  * Created by skramer on 21.12.16.
  */
class FunctionalObjectTest extends FlatSpecWithMatchers {
  "constructor params of other objects" should "not be accessible from this object" in {
    class Foo(i: Int) {
      def add(f: Foo): Unit = {
        assertDoesNotCompile(
          """
            |i + f.i
          """.stripMargin)
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


