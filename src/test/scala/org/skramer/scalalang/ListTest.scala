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

  val oneTwoThree = List(1, 2, 3)
  "list" can "be created using (right-associative) cons" in {
    val listOfInt = 1 :: 2 :: 3 :: Nil
    listOfInt shouldBe oneTwoThree
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

  "insertion sort" can "be implemented using pattern matching" in {
    def isort(xs: List[Int]): List[Int] = {
      def insert(x: Int, xs: List[Int]): List[Int] = {
        xs match {
          case Nil => List(x)
          case y :: ys if x <= y => x :: xs
          case y :: ys if x > y => y :: insert(x, ys)
        }
      }

      xs match {
        case Nil => xs
        case y :: ys => insert(y, isort(ys))
      }
    }

    val listToBeSorted = List(1, 5, 3, 56, 8, 4)
    isort(listToBeSorted) shouldBe List(1, 3, 4, 5, 8, 56)
  }

  "merge sort" can "be implemented with list and pattern matching" in {
    def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
      def merge(xs: List[T], ys: List[T]): List[T] = {
        (xs, ys) match {
          case (Nil, _) => ys
          case (_, Nil) => xs
          case (x :: xs1, y :: ys1) => if (less(x, y)) x :: merge(xs1, ys) else y :: merge(xs, ys1)
        }
      }

      val size = xs.size
      if (size == 1) {
        xs
      }
      else {
        val (ys, zs) = xs.splitAt(size / 2)
        merge(msort(less)(ys), msort(less)(zs))
      }
    }

    val listToBeSorted = List(1, 5, 3, 56, 8, 4)
    msort((_: Int) < (_: Int))(listToBeSorted) shouldBe List(1, 3, 4, 5, 8, 56)
  }

  "list concatenation" can "be implemented using pattern matching" in {
    def concat(xs: List[Int], ys: List[Int]): List[Int] = {
      xs match {
        case Nil => ys
        case z :: zs => z :: concat(zs, ys)
      }
    }

    concat(oneTwoThree, List(4, 5, 6)) should be(1 to 6)
  }

  val abc = List('a', 'b', 'c')
  "list" can "be iterated with indices" in {
    val list = abc
    (for ((el, idx) <- list.zipWithIndex) yield idx -> el).toMap should be(Map(0 -> 'a', 1 -> 'b', 2 -> 'c'))
  }

  "list indices" can "be obtained" in {
    oneTwoThree.indices should be(0 to 2)
  }

  "multidimensional list" can "be flatten" in {
    List(oneTwoThree, List(), List(4, 5, 6)).flatten should be((1 to 6).toList)
  }

  "list" can "be zipped with another list" in {
    (1 to 10).toList zip ('a' to 'z') should have length 10
  }

  "list of tuples" can "be unzipped" in {
    List(1 -> 'a', 2 -> 'b', 3 -> 'c').unzip shouldBe(oneTwoThree, abc)
  }

  "collection" can "be turned into a string" in {
    oneTwoThree.mkString("^", " ", "$") should be("^1 2 3$")
    val sb = new StringBuilder
    oneTwoThree.addString(sb, "[", ",", "]").toString() shouldBe "[1,2,3]"
  }

  "list reversing" can "be implemented using folds" in {
    def reverse(xs: List[Int]): List[Int] = {
      (List[Int]() /: xs) ((acc, x) => x :: acc)
    }

    reverse(oneTwoThree) should be(oneTwoThree.reverse)
  }

  "list" can "be turned into a string using folding" in {
    def concat(xs: List[String]): String = {
      (xs.head /: xs.tail) (_ + " " + _)
    }

    concat(List("quick", "brown", "fox")) shouldBe "quick brown fox"
  }

  "list" can "be flatten using folding" in {
    def flatten(xss: List[List[Int]]): List[Int] = {
      (xss :\ List[Int]()) (_ ::: _)
    }

    flatten(List(oneTwoThree, List(), List(4, 5, 6))) shouldBe List.range(1, 7)
  }

  "list" can "be sorted using provided predicate" in {
    val unsortedList = List(1, 44, 12, 99, 9, 81)
    val sortedList = List(1, 9, 12, 44, 81, 99)
    unsortedList sortWith (_ < _) shouldBe sortedList
    unsortedList sortWith (_ > _) shouldBe sortedList.reverse
  }

  "list range" can "use specific step" in {
    List.range(9, 1, -3) shouldBe List(9, 6, 3)
  }

}
