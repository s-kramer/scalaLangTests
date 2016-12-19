/**
  * Created by skramer on 19.12.16.
  */
class FirstSteps {
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
}
