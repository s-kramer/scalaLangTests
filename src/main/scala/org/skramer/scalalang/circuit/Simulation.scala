package org.skramer.scalalang.circuit

/**
  * Created by skramer on 06.01.17.
  */
abstract class Simulation {
  var currTime: Int = 0

  def currentTime: Int = currTime

  type Action = () => Unit

  class WorkItem(val delay: Int, val action: Action)

  private var agenda: List[WorkItem] = List()

  private def insert(items: List[WorkItem], workItem: WorkItem): List[WorkItem] = {
    if (items.isEmpty || workItem.delay < items.head.delay) {
      workItem :: items
    }
    else {
      items.head :: insert(items.tail, workItem)
    }
  }

  def afterDelay(delay: Int)(block: => Unit): Unit = {
    val workItem = new WorkItem(currentTime + delay, () => block)
    agenda = insert(agenda, workItem)
  }

  private def next(): Unit = {
    (agenda: @unchecked) match {
      case head :: rest =>
        agenda = rest
        currTime = head.delay
        head.action()
    }
  }

  def run(): Unit = {
    afterDelay(0) {
      println(s"Simulation step. Current time: $currTime")
    }

    while (agenda.nonEmpty) next()
  }
}
