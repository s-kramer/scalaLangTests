package org.skramer.scalalang

/**
  * Created by skramer on 19.12.16.
  */
class FirstSteps extends FlatSpecWithMatchers {
  "val variable" should "remain immutable" in {
    val i = 0
    assertDoesNotCompile("i = 1")
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
}
