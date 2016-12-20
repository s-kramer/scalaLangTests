package org.skramer.scalalang

import scala.collection.mutable

/**
  * Created by skramer on 19.12.16.
  */
class FirstSteps extends FlatSpecWithMatchers {
  "val variable" should "remain immutable" in {
    assertDoesNotCompile(
      """
        |val i = 0
        |i = 1
      """.stripMargin)
  }

  "var variable" should "be able to change" in {
    var i = 0
    i = 1
  }

  "variable with specified type" should "be of the same type as the inferred one" in {
    val i: Int = 0
    val j = 0
    i === j
  }

  "function definition" should "contain explicit parameter types" in {
    def max(left: Int, right: Int): Int = if (left > right) left else right

    assertResult(5) {
      max(3, 5)
    }
  }

  "void function definition" should "omit the return type or declare it Unit" in {
    def printMax(left: Int, right: Int): Unit = if (left > right) println(left) else println(right)

    def printMax2(left: Int, right: Int) = if (left > right) println(left) else println(right)

    printMax(3, 5)
    printMax2(3, 5)
  }

  "recursive functions" must "declare result type" in {
    assertDoesNotCompile(
      """
    def recursiveSize(i: Int) = if (i == 0) 0 else 1 + recursiveSize(i - 1)
  """.stripMargin)
  }

  "while loop" should "iterate until false" in {
    var i = 10
    var sum = 0
    assertResult(55) {
      while (i > 0) {
        sum += i
        i -= 1
      }
      sum
    }
  }

  "incrementation and decrementation operators" should "not compile" in {
    assertDoesNotCompile(
      """
        |var i = 0
        |i++
        |++i
        |--i
        |i--
      """.stripMargin)
  }

  "foreach loop" should "call function with side effects on all elements" in {
    val numbers = 1 to 5
    numbers.foreach(num => println(num))
    numbers.foreach((num: Int) => println(num))
    numbers.foreach(println)

    val m = Map[Int, Char](1 -> 'a', 2 -> 'b')
    m.foreach { case (i, j) => println(s"$i is bound to $j") }
  }

  "for expression" should "surprisingly, iterate over elements from generator" in {
    var sum = 0
    assertResult(15) {
      for (number <- 1 to 5) {
        sum += number
      }

      sum
    }
  }

  "parametrized array" should "be iterable" in {
    assertResult("Hello cruel world ") {
      var concatenated = ""
      val strings = new Array[String](3)
      strings(0) = "Hello"
      strings(1) = "cruel"
      strings(2) = "world"

      for (string <- strings) {
        concatenated += string + " "
      }

      concatenated
    }
  }

  "parentheses" should "invoke apply method" in {
    class Hello(val called: Boolean) {
      def apply(): Hello = new Hello(true)
    }

    val h = new Hello(false)
    val appliedHello = h()
    val explicitHello = h.apply()
    assert(appliedHello.called)
    assert(explicitHello.called)
    assert(!h.called)
  }

  "parentheses assignment" should "invoke update method" in {
    class FooCollection() {
      var lastIndex = 0
      var lastValue = true

      def update(index: Int, value: Boolean): Unit = {
        lastIndex = index
        lastValue = value
      }
    }

    val f = new FooCollection
    f(1) = false
    assert(!f.lastValue)
    assertResult(1) {
      f.lastIndex
    }
  }

  "array's companion apply method" should "create new array" in {
    val a1 = Array("zero", "one", "two")
    val a2 = Array.apply("zero", "one", "two", "three")

    a1 should have length 3
    a2 should have length 4
  }

  "concat operator" should "concatenate two lists" in {
    val first = List(1, 2)
    val second = List(3, 4)

    first ::: second should have size 4
    first should have size 2
    second should have size 2
  }

  "cons operator" should "prepend element to list" in {
    val first = List(1, 2, 3)

    first should have size 3
    0 :: first should have size 4
  }

  "nil" should "represent empty list" in {
    Nil should have size 0
    1 :: 2 :: 3 :: Nil should have size 3
  }

  "tuple" can "contain elements of different types" in {
    val tuple = (1, "two", "III", 4, 'V')
    assertResult(1) {
      tuple._1
    }
    assertResult("two") {
      tuple._2
    }
    assertResult("III") {
      tuple._3
    }
    assertResult(4) {
      tuple._4
    }
    assertResult('V') {
      tuple._5
    }
  }

  "immutable set" can "not be extended with another element " in {
    assertDoesNotCompile(
      """
        | val s = Set("one", "two")
        | s += two
      """.stripMargin)
  }

  "new set" can "be created using old one" in {
    val set = Set("one", "two")
    val extendedSet = set + "three"

    set should have size 2
    extendedSet should have size 3
  }

  "mutable set" can "be extended with new elements" in {
    val set = mutable.Set("one", "two")
    set += "three"
    set should have size 3
  }
}
