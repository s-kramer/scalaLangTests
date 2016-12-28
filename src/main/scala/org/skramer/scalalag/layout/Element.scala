package org.skramer.scalalag.layout

/**
  * Created by skramer on 23.12.16.
  */
abstract class Element {
  require(content.forall(_.length == content(0).length))

  private def widen(minimalWidth: Int) = {
    if (width >= minimalWidth) {
      this
    }
    else {
      val left = Element(' ', (minimalWidth - width) / 2, height)
      val right = Element(' ', minimalWidth - width - left.width, height)
      left beside this beside right
    }
  }

  def heighten(minimalHeight: Int): Element = {
    if (height >= minimalHeight) {
      this
    }
    else {
      val upper = Element(' ', width, (minimalHeight - height) / 2)
      val lower = Element(' ', width, minimalHeight - height - upper.height)
      upper above this above lower
    }
  }

  def width: Int = if (content.length == 0) 0 else content(0).length

  def height: Int = content.length

  def content: Array[String]

  def beside(other: Element): Element = {
    val this1 = this heighten other.height
    val that1 = other heighten height
    new ArrayElement(for {(line1, line2) <- this1.content zip that1.content}
      yield line1 + line2)
  }

  def above(other: Element): Element = {
    val this1 = this widen other.width
    val that1 = other widen this.width
    new ArrayElement(this1.content ++ that1.content)
  }

  override def toString: String = s"${content.mkString("\n")}"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Element]

  override def equals(other: Any): Boolean = other match {
    case that: Element =>
      (that canEqual this) &&
        width == that.width &&
        height == that.height &&
        (content sameElements that.content)
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(width, height, content)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object Element {

  def apply(content: Array[String]): Element = new ArrayElement(content)

  def apply(content: String): Element = new LineElement(content)

  def apply(char: Char, width: Int, height: Int): Element = new UniformElement(char, width, height)

}
