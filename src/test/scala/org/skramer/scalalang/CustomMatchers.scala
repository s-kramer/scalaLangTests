package org.skramer.scalalang

import org.scalatest.Matchers
import org.scalatest.matchers.{BeMatcher, MatchResult}

/**
  * Created by skramer on 22.12.16.
  */
trait CustomMatchers extends Matchers {

  class OddMatcher extends BeMatcher[Int] {
    def apply(left: Int) =
      MatchResult(
        left % 2 !== 0,
        left.toString + " was even",
        left.toString + " was odd"
      )
  }

  val odd: BeMatcher[Int] = new OddMatcher
  val even: BeMatcher[Int] = not(odd)
}

object CustomMatchers extends CustomMatchers
