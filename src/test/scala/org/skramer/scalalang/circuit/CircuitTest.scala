package org.skramer.scalalang.circuit

import org.skramer.scalalang.FlatSpecWithMatchers
import org.skramer.scalalang.circuit.MyCircuitSimulator._

/**
  * Created by skramer on 06.01.17.
  */
class CircuitTest extends FlatSpecWithMatchers {
  "full adder" should "add signals with carry" in {

    val a, b, sum, carryOut = new Wire
    probe(sum, "sum")
    probe(carryOut, "carryOut")
    halfAdder(a, b, sum, carryOut)

    a setSignal true
    MyCircuitSimulator.run()
    sum.getSignal shouldBe true
    carryOut.getSignal shouldBe false

    b setSignal true
    MyCircuitSimulator.run()
    carryOut.getSignal shouldBe true
    sum.getSignal shouldBe false
  }
}
