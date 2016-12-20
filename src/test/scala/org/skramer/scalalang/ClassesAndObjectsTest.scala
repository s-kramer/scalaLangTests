package org.skramer.scalalang

/**
  * Created by skramer on 20.12.16.
  */
class ClassesAndObjectsTest extends FlatSpecWithMatchers {
  "private member fields" can "not be accessed from outside" in {
    class Foo {
      private val sum = 0
    }
    val f = new Foo
    assertDoesNotCompile(
      """
        |f.sum
      """.stripMargin)
  }

  "method parameters" can "not be changed" in {
    def foo(i: Int) {
      assertDoesNotCompile(
        """
          |i = 1
        """.stripMargin
      )
    }
  }
}
