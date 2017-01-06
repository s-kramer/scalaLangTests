package org.skramer.scalalang.circuit

/**
  * Created by skramer on 06.01.17.
  */
object MyCircuitSimulator extends CircuitSimulation {
  override val InverterGateDelay: Int = 5
  override val AndGateDelay: Int = 7
  override val OrGateDelay: Int = 6
}
