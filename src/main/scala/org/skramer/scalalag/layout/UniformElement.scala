package org.skramer.scalalag.layout

/**
  * Created by skramer on 23.12.16.
  */
class UniformElement private[layout](character: Char, width: Int, height: Int) extends Element {
  override def content: Array[String] = Array.fill(height)(character.toString * width)
}
