import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by skramer on 19.12.16.
  */
class SanityTest extends FlatSpec with Matchers {
  "sanity test" should "pass" in {
    assertResult(true)
    {
      true
    }
  }

  "val variable" should "remain immutable" in {
    val i = 0
    assertDoesNotCompile("i = 1")
  }

  "var variable" should "be able to change" in {
    var i = 0
    i = 1
  }
}
