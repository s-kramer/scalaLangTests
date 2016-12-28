package org.skramer.scalalang

import org.skramer.scalalag.layout.Element

/**
  * Created by skramer on 28.12.16.
  */
class ScalaHierarchyTest extends FlatSpecWithMatchers {
  "==" should "compare using structural equality" in {
    val first = Element("qwerty")
    val second = Element("qwerty")

    assert(first == second)
  }

  "eq" should "compare using referential equality" in {
    val first = Element("qwerty")
    val second = Element("qwerty")

    assert(!(first eq second))
    assert(first eq first)
    assert(second eq second)
  }

  "value classes" should "not be instantiable using new" in {
    assertDoesNotCompile(
      """
        |new Int(5)
      """.stripMargin)
  }
}
