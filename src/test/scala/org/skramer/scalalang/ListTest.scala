package org.skramer.scalalang

/**
  * Created by skramer on 01.01.17.
  */
class ListTest extends FlatSpecWithMatchers {
  "list" should "be covariant" in {
    val listOfInt = List(1, 2, 3, 4)
    val listOfAny: List[Any] = listOfInt

    listOfAny should be(a[List[_]])
  }

  "list" should "not be contravariant" in {
    val listOfAny: List[Any] = List(1, 2, 3, 4)
    assertDoesNotCompile(
      """"
        |val listOfInt: List[Int] = listOfAny
      """)
  }

  "list of Nothing" should "be assignable to list of any type" in {
    val listOfNothing: List[Nothing] = List()
    val listOfInt: List[Int] = listOfNothing
  }

  "list" can "be created using (right-associative) cons" in {
    val listOfInt = 1 :: 2 :: 3 :: Nil
    listOfInt shouldBe List(1, 2, 3)
  }
}
