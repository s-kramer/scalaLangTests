package org.skramer.scalalang.spiral

import org.skramer.scalalang.`implicit`.ImplicitConversions.AnyOps
import org.skramer.scalalang.layout.Element

object Spiral {
  val space = Element(" ")
  val corner = Element("+")

  def spiral(nEdges: Int, direction: Int): Element = {
    if (nEdges === 1) {
      Element("+")
    }
    else {
      val sp = spiral(nEdges - 1, (direction + 3) % 4)

      def verticalBar = Element('|', 1, sp.height)

      def horizontalBar = Element('-', sp.width, 1)

      if (direction === 0) {
        (corner beside horizontalBar) above (sp beside space)
      }
      else if (direction === 1) {
        (sp above space) beside (corner above verticalBar)
      }
      else if (direction === 2) {
        (space beside sp) above (horizontalBar beside corner)
      }
      else {
        (verticalBar above corner) beside (space above sp)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val nSides = args(0).toInt
    println(spiral(nSides, 0))
  }
}

