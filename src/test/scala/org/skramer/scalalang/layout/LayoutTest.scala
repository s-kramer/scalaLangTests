package org.skramer.scalalang.layout

import org.skramer.scalalag.layout.{ArrayElement, LineElement, UniformElement}
import org.skramer.scalalang.FlatSpecWithMatchers

/**
  * Created by skramer on 23.12.16.
  */
class LayoutTest extends FlatSpecWithMatchers {
  "single line element" should "have proper properties" in {
    val singleLineElement = new LineElement("Hello")

    assert(singleLineElement.width == "Hello".length)
    assert(singleLineElement.height == 1)
  }

  "multi line element" should "have proper properties" in {
    val multiLineElement = new ArrayElement(Array("hello", "world"))

    assert(multiLineElement.width == "hello".length)
    assert(multiLineElement.height == 2)
  }

  "uniform element" should "have proper properties" in {
    val uniformElement = new UniformElement("-", 5, 2)

    assert(uniformElement.width == 5)
    assert(uniformElement.height == 2)
  }

  "element's content" can "be widen" in {
    val first = new LineElement("hello")
    val second = new LineElement("world")

    val widen = first beside second

    assert(widen.width == "hello".length + "world".length)
    assert(widen.height == 1)
  }

  "element's content" can "be broaden" in {
    val first = new LineElement("hello")
    val second = new LineElement("world")

    val extended = first above second

    assert(extended.width == "hello".length)
    assert(extended.height == 2)
  }
}
