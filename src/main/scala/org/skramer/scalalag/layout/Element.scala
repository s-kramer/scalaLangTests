package org.skramer.scalalag.layout

/**
  * Created by skramer on 23.12.16.
  */
abstract class Element {
  require(content.nonEmpty)
  val width: Int = content(0).length

  val height: Int = content.length

  def content: Array[String]
}
