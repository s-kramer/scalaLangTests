package org.skramer.scalalang.layout

import org.skramer.scalalag.layout.Element
import org.skramer.scalalang.FlatSpecWithMatchers

/**
  * Created by skramer on 23.12.16.
  */
class LayoutTest extends FlatSpecWithMatchers {
  "single line element" should "have proper properties" in {
    val singleLineElement = Element("Hello")

    assert(singleLineElement.width == "Hello".length)
    assert(singleLineElement.height == 1)
  }

  "multi line element" should "have proper properties" in {
    val multiLineElement = Element(Array("hello", "world"))

    assert(multiLineElement.width == "hello".length)
    assert(multiLineElement.height == 2)
  }

  "uniform element" should "have proper properties" in {
    val uniformElement = Element('-', 5, 2)

    assert(uniformElement.width == 5)
    assert(uniformElement.height == 2)
  }

  "element's content" can "be widen" in {
    val first = Element("hello")
    val second = Element("world")

    val widen = first beside second

    assert(widen.width == "hello".length + "world".length)
    assert(widen.height == 1)
  }

  "element's content" can "be broaden" in {
    val first = Element("hello")
    val second = Element("world")

    val extended = first above second

    assert(extended.width == "hello".length)
    assert(extended.height == 2)
  }

  "elements with different widths" can "be put above each other" in {
    val first = Element("hello")
    val second = Element("long text")

    val extended = first above second

    extended.width shouldBe "long text".length
    extended.height shouldBe 2
    extended.content(0) shouldBe "  hello  "
    extended.content(1) shouldBe "long text"
  }

  "elements with different heights" can "be put beside each other" in {
    val first = Element(Array("hello", "world"))
    val second = Element(Array("hello"))

    val extended = first beside second

    extended.width shouldBe "hellohello".length
    extended.height shouldBe 2
  }

  "elements with different widths and heights" can "be put above each other" in {
    val first = Element(Array("hello", "world"))
    val longText = "this is a very long text"
    val second = Element(Array(longText))

    val extended = first above second

    extended.width shouldBe longText.length
    extended.height shouldBe 3
    extended.content(0) shouldBe " " * 9 + "hello" + " " * 10
    extended.content(1) shouldBe " " * 9 + "world" + " " * 10
    extended.content(2) shouldBe longText
  }

  "array elements with inconsistent widths" should "throw" in {
    assertThrows[IllegalArgumentException](Element(Array("hello", "very long text")))
  }
}
