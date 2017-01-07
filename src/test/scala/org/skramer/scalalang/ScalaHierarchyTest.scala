package org.skramer.scalalang

import org.skramer.scalalang.layout.Element

/**
  * Created by skramer on 28.12.16.
  */
class ScalaHierarchyTest extends FlatSpecWithMatchers {
  "==" should "compare using structural equality" in {
    val first = Element("qwerty")
    val second = Element("qwerty")

    assert(first === second)
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

  "new value class" can "be defined by subclassing AnyVal with only one field" in {
    class MyNewVal(val value: Int) {
      //backed by int
      override def toString: String = s"myValue: $value"
    }

    val argument = 15
    val v = new MyNewVal(argument)
    v.value shouldBe argument
    v.toString shouldBe s"myValue: $argument"
  }

  "Nothing" should "derive from every class" in {
    class Foo
    def throwRuntimeException: Nothing = {
      throw new RuntimeException
    }

    def f: Foo = if (false) new Foo else throwRuntimeException

    assertThrows[RuntimeException](f)
  }
}
