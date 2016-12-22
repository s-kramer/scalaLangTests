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

  "partially applied function" should "apply new arguments to current function definition" in {
    val f: (Int) => Int = 1 + _ + 3
    f(5) shouldBe 9
  }

  "it" should "also wait for all remaining arguments" in {
    def sum(i: Int, j: Int, k: Int) = i + j + k

    val f3 = sum _
    val f2 = f3(1, _: Int, _: Int)
    // explicit types of underscores required as they constitute a function value only, not eta expansion
    // http://stackoverflow.com/questions/2363013/in-scala-why-cant-i-partially-apply-a-function-without-explicitly-specifying-i
    val f1 = f2(2, _: Int)

    f1(3) shouldBe 6
  }

  "it" should "also wait for all remaining arguments curried version" in {
    def sum(i: Int)(j: Int)(k: Int) = i + j + k

    val f3 = sum _
    val f2 = f3(1)
    val f1 = f2(2)

    f1(3) shouldBe 6
  }
}
