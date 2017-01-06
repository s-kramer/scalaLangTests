package org.skramer.scalalang.circuit

/**
  * Created by skramer on 06.01.17.
  */
abstract class BasicCircuitSimulation extends Simulation {
  val InverterGateDelay: Int
  val AndGateDelay: Int
  val OrGateDelay: Int

  class Wire {
    private var sigVal = false

    var actions: List[Action] = List()

    def getSignal: Boolean = sigVal

    def setSignal(newSignalValue: Boolean): Unit = {
      if (sigVal != newSignalValue) {
        sigVal = newSignalValue
        actions foreach (_ ())
      }
    }

    def addAction(action: Action): Unit = {
      actions = action :: actions
      action()
    }
  }

  def inverter(input: Wire, output: Wire): Unit = {
    def inverterAction(): Unit = {
      afterDelay(InverterGateDelay) {
        output setSignal !input.getSignal
      }
    }

    input addAction inverterAction
  }

  def orGate(a1: Wire, a2: Wire, output: Wire): Unit = {
    def orAction() = {
      afterDelay(OrGateDelay) {
        output setSignal a1.getSignal || a2.getSignal
      }
    }

    a1 addAction orAction
    a2 addAction orAction
  }

  def andGate(a1: Wire, a2: Wire, output: Wire): Unit = {
    def andAction() = {
      afterDelay(AndGateDelay) {
        output setSignal a1.getSignal && a2.getSignal
      }
    }

    a1 addAction andAction
    a2 addAction andAction
  }

  def probe(input: Wire, name: String): Unit = {
    def probeAction() = {
      println(s"Signal $name at moment $currentTime has value ${input.getSignal}")
    }

    input addAction probeAction
  }

}
