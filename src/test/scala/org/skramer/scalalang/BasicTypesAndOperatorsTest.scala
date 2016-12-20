package org.skramer.scalalang

/**
  * Created by skramer on 20.12.16.
  */
class BasicTypesAndOperatorsTest extends FlatSpecWithMatchers {
  "symbols" should "represent their names" in {
    val symbol: Symbol = 'symbol
    assertResult("symbol") {
      symbol.name
    }
  }

  "symbols" should "be internalized" in {
    val symbol: Symbol = 'symbol
    symbol should be theSameInstanceAs Symbol("symbol")
  }

  "raw interpolator" should "ignore escape signs" in {
    raw"\\\\" should have length 4
  }

  "f interpolator" should "default to string interpolator" in {
    val h = "hello"
    f"$h" should have length 5
  }

  "f interpolator" should "use printf syntax" in {
    f"${math.Pi}%.5f" should fullyMatch regex (raw"(\d)\.(\d+)" withGroups("3", "14159"))
  }

  "operator notation invocation with multiple arguments" must "use parenthesess" in {
    assertResult(8) {
      "hello, world" indexOf('o', 5) //operator notation
    }
    "hello, world".indexOf('o', 5) //non-operator notation
  }

  "unary prefix operator" should "have shorthand version" in {
    val minusTwo = -2
    assertResult(minusTwo) {
      2.unary_-
    }
  }

  "unary postfix operator" can "be called without parentheses (if it doesn't have side effects)" in {
    assert(!(("Hello, World!" toLowerCase) exists (_.isUpper)))
  }

  "equality operator" should "compare objects of different types" in {
    assertResult(false) {
      List(1, 2, 3) == true
    }
  }

  "equality operator" should "check left handside for null first" in {
    assertResult(false) {
      null == List(1, 2, 3)
    }
  }

  "equality operator" should "compare based on the contents" in {
    assertResult(true) {
      List(1, 2, 3).toSet == Set(1, 2, 3)
    }
  }

  "eq operator" should "use referential equality" in {
    class Foo
    val f = new Foo
    assertResult(false) {
      f.eq(new Foo)
    }
    assertResult(true) {
      f.eq(f)
    }
  }
}
