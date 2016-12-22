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

}
