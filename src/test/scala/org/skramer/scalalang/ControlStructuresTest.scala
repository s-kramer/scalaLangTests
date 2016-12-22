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
}
