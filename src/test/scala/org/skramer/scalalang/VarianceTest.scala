package org.skramer.scalalang

/**
  * Created by skramer on 07.01.17.
  */
class VarianceTest extends FlatSpecWithMatchers {
  "mutable structures" should "be invariant" in {
    val arrayOfInt: Array[Int] = Array(1, 2, 3)
    assertDoesNotCompile(
      """
        | val arrayOfAny: Array[Any] = arrayOfInt /* this could lead to assigning a non-int to Array[Int] using reference of type Array[Any] */
      """.stripMargin)

    /**
      * Note: for the same reason (mutability) the java.util.List must be invariant. Otherwise you could trick the type
      * system.
      */
  }

  "scala immutable list" should "be covariant" in {
    val listOfInt: List[Int] = List(1, 2, 3)
    val listOfAny = listOfInt // this is safe as list is immutable

    val modifiedListOfAny: List[Any] = listOfAny ++ List("String", "string") // new list is created,

    listOfAny shouldBe List(1, 2, 3) // no invalid operation can be made on List[Any] as it's immutable
  }

  "classes that are producers" should "be declared covariant" in {
    abstract class Producer[+A] {
      def produce: A
    }

    class Foo
    class Bar extends Foo

    val barProducer = new Producer[Bar]() {
      override def produce: Bar = new Bar
    }

    val fooProducer: Producer[Foo] = barProducer

    fooProducer.produce shouldBe a[Bar]
  }
}
