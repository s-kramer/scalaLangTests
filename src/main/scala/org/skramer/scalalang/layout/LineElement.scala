package org.skramer.scalalang.layout

/**
  * Created by skramer on 23.12.16.
  */
class LineElement private[layout](val cont: String) extends Element {
  override def content: Array[String] = Array(cont)
}
