package org.skramer.scalalang

import org.skramer.scalalang.rational.Rational

/**
  * Created by skramer on 21.12.16.
  */
class RationalTest extends FlatSpecWithMatchers {
  "newly created rational" should "have assigned nominator and denominator" in {
    val r = new Rational(1, 2)
    assertResult(1) {
      r.nominator
    }
    assertResult(2) {
      r.denominator
    }
  }

  "rational adding" should "create new rational" in {
    val half = new Rational(1, 2)
    val oneThird = new Rational(1, 3)
    val sum = half.add(oneThird)
    half.nominator shouldBe 1
    half.denominator shouldBe 2
    oneThird.nominator shouldBe 1
    oneThird.denominator shouldBe 3
    sum.nominator shouldBe 5
    sum.denominator shouldBe 6
  }

}
