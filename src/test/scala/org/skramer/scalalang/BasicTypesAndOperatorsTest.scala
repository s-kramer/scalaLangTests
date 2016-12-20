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

}
