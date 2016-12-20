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
}
