package org.skramer.scalalang

/**
  * Created by skramer on 22.12.16.
  */
class MethodTest extends FlatSpecWithMatchers {
  "nested functions" should "be able to reach variables in enclosing scope" in {
    def foo(i: Int): Int = {
      def bar(): Int = i * 2

      bar()
    }

    foo(1) shouldBe 2
  }

  "reassignable function literal" should "be able to change" in {
    var f = (x: Int) => x + 1
    f(1) shouldBe 2
    f = (x: Int) => x + 5
    f(1) shouldBe 6
  }

  "underscore notation" should "create function values" in {
    List(1, 2, 3, 4, 5).filter(_ >= 5) shouldBe List(5)
  }

  "multiple underscores" should "refer to subsequent arguments" in {
    val f = (_: Int) + (_: Int)
    f(1, 2) shouldBe 3
  }
}
