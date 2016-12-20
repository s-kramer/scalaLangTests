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

  "semicolon" should "be placed between multiple invocations in one line" in {
    assertCompiles("val i = 1; println(i)")
  }

  "method chaining" should "use operator as last statement" in {
    val i = 1
    val j = 2
    val k = 3
    assertResult(6) {
      val sum = i +
        j +
        k
      sum
    }
  }

  "companion object" can "access private fields of companion class" in {
    class Foo {
      private val bar = 1
    }

    object Foo {
      def hello(f: Foo): Unit = {
        assertCompiles(
          """
            |f.bar
          """.stripMargin)
      }
    }
  }

  "singleton object" should "be initialized on first usage" in {
    var bar = 0
    object Foo {
      bar = 1

      def dummy(): Unit = {

      }
    }

    assertResult(0) {
      bar
    }

    Foo.dummy()

    assertResult(1) {
      bar
    }

  }

  "symbols" should "represent their names" in {
    val symbol: Symbol = 'symbol
    assertResult("symbol") {
      symbol.name
    }
  }

  "symbols" should "be internalized" in {
    val symbol: Symbol = 'symbol
    symbol should be theSameInstanceAs Symbol("symbol")
  }
}
