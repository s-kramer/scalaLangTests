package org.skramer.scalalag.layout

/**
  * Created by skramer on 23.12.16.
  */
class UniformElement(character: String, width: Int, height: Int) extends Element {
  override def content: Array[String] = Array.fill(height)(character * width)
}
