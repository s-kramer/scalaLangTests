import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by skramer on 19.12.16.
  */
class SanityTest extends FlatSpecWithMatchers {
  "sanity test" should "pass" in {
    assertResult(true)
    {
      true
    }
  }

}
