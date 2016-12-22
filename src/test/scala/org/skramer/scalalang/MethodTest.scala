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

  "closure" should "fail if free variable is not found" in {
    //    val secondParam = 2
    assertDoesNotCompile(
      """
        |val f = (x: Int) => x + secondParam
        |f(2)
      """.stripMargin)
  }

  "closure" should "use free variable available during its creation (invocation)" in {
    var secondParam = 2
    val f = (x: Int) => x + secondParam
    f(5) shouldBe 7

    secondParam = 15
    f(5) shouldBe 20
  }

  "clojure" can "modify free variable" in {
    var sum = 0
    (1 to 5).foreach(sum += _)
    sum shouldBe 15
  }

  "vararg" should "generate multiple arguments" in {
    def f(strings: String*): String = {
      strings.mkString(" ")
    }

    f("1") shouldBe "1"
    f("1", "2") shouldBe "1 2"
    f("1", "2", "15") shouldBe "1 2 15"
  }

  "vararg method" can "accept extracted array" in {
    def f(strings: String*): String = {
      strings.mkString(" ")
    }

    val args = Array("1", "2", "15")
    f(args: _*) shouldBe "1 2 15"
  }

  "named arguments" can "be provided out of order" in {
    def f(arg1: Int, arg2: Int): Int = 10 * arg1 + arg2

    f(arg2 = 11, arg1 = 5) shouldBe 61
  }

  "named and default parameters" should "mix with each other" in {
    def f(arg1: Int = 5, arg2: Int = 11): Int = 10 * arg1 + arg2

    f() shouldBe 61
    f(arg1 = 0) shouldBe 11
    f(arg1 = 12, arg2 = 8) shouldBe 128
  }

  "tail call optimization" should "be applied automatically where possible" in {

    def throwAfterNIterations(n: Int): Int = {
      if (n == 0) throw new RuntimeException
      else throwAfterNIterations(n - 1)
    }

    val ex = intercept[RuntimeException] {
      throwAfterNIterations(3)
    }

    ex.getStackTrace.count(ste => ste.getMethodName.contains("throwAfterNIterations")) should be <= 3
  }

  "tail call optimization" can "not be applied if recursive call is not last" in {
    def throwAfterNIterations(n: Int): Int = {
      if (n == 0) throw new RuntimeException
      else throwAfterNIterations(n - 1) + 1
    }

    val ex = intercept[RuntimeException] {
      throwAfterNIterations(3)
    }

    ex.getStackTrace.count(ste => ste.getMethodName.contains("throwAfterNIterations")) should be > 3
  }
}
