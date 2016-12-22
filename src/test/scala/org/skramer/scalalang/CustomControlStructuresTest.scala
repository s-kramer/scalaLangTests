package org.skramer.scalalang

/**
  * Created by skramer on 22.12.16.
  */
class CustomControlStructuresTest extends FlatSpecWithMatchers {
  "by-name parameters" can "omit function signature parentheses" in {
    def f(param: => Boolean): Unit = if (!param) throw new RuntimeException

    assertThrows[RuntimeException](f(3 > 5))
  }

}
