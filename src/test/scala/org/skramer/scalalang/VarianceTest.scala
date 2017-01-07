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

  "classes that are consumers" should "be declared contravariant" in {
    class Consumer[-A] {
      def consume(arg: A): Unit = {}
    }

    class Foo
    class Bar extends Foo

    val fooConsumer: Consumer[Foo] = new Consumer[Foo]
    val barConsumer: Consumer[Bar] = fooConsumer

    barConsumer.consume(new Bar)
  }

  "classes that are both consumers and producers" should "be invariant" in {
    // no + or - is allowed in type parameter of this class!
    class ProducerConsumer[A] {
      def produce: A = ???

      def consume(arg: A): Unit = ???
    }
  }

  "class with getters and setters" should "not be covariant as it's a special case of no-type-parameter-as-method-argument rule" in {
    class Foo[+A] {
      val a: A = ??? //vals are not reassignable
      assertDoesNotCompile(
        """
          | var varA : A // vars have setters generated that take type-parametrized argument
        """.stripMargin)
    }
  }
}
