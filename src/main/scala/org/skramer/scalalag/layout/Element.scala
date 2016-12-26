package org.skramer.scalalag.layout

/**
  * Created by skramer on 23.12.16.
  */
abstract class Element {
  def width: Int = content(0).length

  def height: Int = content.length

  def content: Array[String]

  def beside(other: Element): Element = {
    new ArrayElement(for {(line1, line2) <- content zip other.content}
      yield line1 + line2)
  }

  def above(other: Element): Element = {
    new ArrayElement(content ++ other.content)
  }

  override def toString = s"Element($width, $height, $content)"
}

object Element {

  def apply(content: Array[String]): Element = new ArrayElement(content)

  def apply(content: String): Element = new LineElement(content)

  def apply(char: Char, width: Int, height: Int): Element = new UniformElement(char, width, height)

}
