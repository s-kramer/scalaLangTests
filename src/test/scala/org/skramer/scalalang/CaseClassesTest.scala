package org.skramer.scalalang

/**
  * Created by skramer on 30.12.16.
  */
class CaseClassesTest extends FlatSpecWithMatchers {
  "case classes" should "get a factory method, fields and method implementations by default" in {
    case class Foo(value: Int)
    val first, second = Foo(5)
    first.value should be(5)
    second.value should be(5)
    assert(first == second)
    first.toString shouldBe "Foo(5)"
  }

  "case classes" can "be copied with partial modifications" in {
    case class Foo(val1: Int, val2: Int)
    val first = Foo(5, 10)
    val second = first.copy(val2 = 15)

    first.val1 shouldBe 5
    first.val2 shouldBe 10
    second.val1 shouldBe 5
    second.val2 shouldBe 15
  }

  "case classes" can "be pattern matched" in {
    case class Foo(v: Int, k: String)
    Foo(5, "bar") match {
      case Foo(_, "bar") => succeed
      case _ => fail()
    }
  }
}
