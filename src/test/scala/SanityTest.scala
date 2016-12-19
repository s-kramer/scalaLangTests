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
}
