package org.skramer.scalalang.circuit

/**
  * Created by skramer on 06.01.17.
  */
abstract class CircuitSimulation extends BasicCircuitSimulation {
  def halfAdder(a: Wire, b: Wire, sum: Wire, carry: Wire): Unit = {
    val d, e = new Wire
    orGate(a, b, d)
    andGate(a, b, carry)
    inverter(carry, e)
    andGate(d, e, sum)
  }

  def fullAdder(a: Wire, b: Wire, carryIn: Wire, sum: Wire, carryOut: Wire): Unit = {
    val s, c1, c2 = new Wire
    halfAdder(a, carryIn, s, c1)
    halfAdder(b, s, sum, c2)
    orGate(c1, c2, carryOut)
  }
}
