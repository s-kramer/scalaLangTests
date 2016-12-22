package org.skramer.scalalang

import org.skramer.scalalang.CustomMatchers._

/**
  * Created by skramer on 22.12.16.
  */
class ControlStructuresTest extends FlatSpecWithMatchers {
  "for loop" should "iterate over all generates" in {
    val nums = {
      for (i <- 1 to 5) yield i
    }

    nums should have size 5
  }

  "filtered for loop" should "not contain filtered out elements" in {
    val evenNumbersUnderTen = for {i <- 1 to 10 if i % 2 == 0} yield i
    all(evenNumbersUnderTen) should not be odd
  }

  "all of multiple conditions in for loop" should "should be applied" in {
    val numbers = for {i <- 1 to 10 if i % 2 == 0
                       j = i * 3 if i > 5} yield j
    all(numbers) should not be odd
    numbers shouldBe List(18, 24, 30)

  }

  "throw clause" should "be a subtype of all types" in {
    assertCompiles(
      """
        |if (false) 2
        |else throw new RuntimeException
      """.stripMargin)
  }

  "exception catching" should "use pattern matching" in {
    try {
      if (false) 2 else throw new RuntimeException
      fail("should have thrown an exception")
    } catch {
      case ex: RuntimeException => succeed
    }
  }

  "finally block without explicit return" should "not change returned value" in {
    def f(): Int = try {
      2
    } finally {
      4
    }

    f() shouldBe 2
  }

  "finally block with explicit return" should "change returned value" in {
    def f(): Int = try {
      return 2
    } finally {
      return 4
    }

    f() shouldBe 4
  }
}
