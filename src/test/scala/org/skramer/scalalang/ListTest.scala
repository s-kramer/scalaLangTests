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

  "insertion sort algorithm" can "be implementable using list" in {
    def isort(list: List[Int]): List[Int] = {
      if (list.isEmpty) Nil
      else insert(list.head, isort(list.tail))
    }

    def insert(x: Int, xs: List[Int]): List[Int] = {
      if (xs.isEmpty || x <= xs.head) x :: xs
      else xs.head :: insert(x, xs.tail)
    }

    val listToBeSorted = List(1, 5, 3, 56, 8, 4)
    isort(listToBeSorted) shouldBe List(1, 3, 4, 5, 8, 56)
  }
}
