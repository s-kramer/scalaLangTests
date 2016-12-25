package org.skramer.scalalag.layout

/**
  * Created by skramer on 23.12.16.
  */
abstract class Element {
  require(content.nonEmpty)

  val width: Int = content(0).length

  val height: Int = content.length

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
