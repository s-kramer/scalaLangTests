package org.skramer.scalalang

import org.skramer.scalalang.rational.Rational

/**
  * Created by skramer on 21.12.16.
  */
class RationalTest extends FlatSpecWithMatchers {
  "newly created rational" should "have assigned nominator and denominator" in {
    val r = new Rational(1, 2)

    r.nominator shouldBe 1
    r.denominator shouldBe 2
  }

  "rational adding" should "create new rational" in {
    val half = new Rational(1, 2)
    val oneThird = new Rational(1, 3)
    val sum = half + oneThird

    half.nominator shouldBe 1
    half.denominator shouldBe 2
    oneThird.nominator shouldBe 1
    oneThird.denominator shouldBe 3
    sum.nominator shouldBe 5
    sum.denominator shouldBe 6
  }

  "rationales with default denominator" can "be created without specifying denominator" in {
    val r = new Rational(5)

    r.nominator shouldBe 5
    r.denominator shouldBe 1
  }

  "values passed to rational constructor" should "get normalized" in {
    val r = new Rational(77, 14)

    r.nominator shouldBe 11
    r.denominator shouldBe 2
  }

  "rational multiplying" should "create new rational with result" in {
    val r1 = new Rational(1, 2)
    val r2 = new Rational(3, 4)
    val product = r1 * r2

    product.nominator shouldBe 3
    product.denominator shouldBe 8
    r1.nominator shouldBe 1
    r1.denominator shouldBe 2
    r2.nominator shouldBe 3
    r2.denominator shouldBe 4
  }

  "operator precedence" should "be preserved" in {
    val r1 = new Rational(1, 2)
    val r2 = new Rational(3, 4)
    val r3 = new Rational(5, 8)
    val result = r3 + r1 * r2

    result.nominator shouldBe 1
    result.denominator shouldBe 1
  }

  "rational subtraction" should "work" in {
    val r1 = new Rational(1, 2)
    val r2 = new Rational(3, 4)
    val difference = r2 - r1
    difference.nominator shouldBe 1
    difference.denominator shouldBe 4
  }

  "rational division" should "work" in {
    val r1 = new Rational(1, 2)
    val r2 = new Rational(3, 4)
    val quotient = r2 / r1
    quotient.nominator shouldBe 3
    quotient.denominator shouldBe 2
  }

  "adding rational and int" should "work" in {
    val r1 = new Rational(1, 2)
    val sum = r1 + 2
    sum.nominator shouldBe 5
    sum.denominator shouldBe 2
  }

  "subtracting rational and int" should "work" in {
    val r1 = new Rational(1, 2)
    val sum = r1 - 2
    sum.nominator shouldBe -3
    sum.denominator shouldBe 2
  }

  "multiplying rational and int" should "work" in {
    val r1 = new Rational(1, 2)
    val sum = r1 * 2
    sum.nominator shouldBe 1
    sum.denominator shouldBe 1
  }

  "dividing rational and int" should "work" in {
    val r1 = new Rational(1, 2)
    val sum = r1 / 2
    sum.nominator shouldBe 1
    sum.denominator shouldBe 4
  }
}
