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
}
