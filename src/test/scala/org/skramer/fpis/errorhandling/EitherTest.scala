package org.skramer.fpis.errorhandling

import org.skramer.scalalang.FlatSpecWithMatchers

/**
  *
  *
  * @author created: skramer on 19.05.18 12:38
  */
class EitherTest extends FlatSpecWithMatchers {
  private val MAGIC_NUMBER = 5
  private val TWICE_MAGIC_NUMBER = 10
  private val LEFT: Either[String, Int] = Left[String]("error")
  private val OTHER_LEFT: Either[String, Int] = Left[String]("error2")
  private val RIGHT: Either[String, Int] = Right(MAGIC_NUMBER)
  private val OTHER_RIGHT: Either[String, Int] = Right(TWICE_MAGIC_NUMBER)
  private val RIGHT_STRING: Either[String, String] = Right(MAGIC_NUMBER.toString)

  "Left" should "remain Left after mapping" in {
    LEFT map (_.toString) shouldBe LEFT
  }

  it should "get default Either on orElse" in {
    LEFT orElse RIGHT shouldBe RIGHT
  }

  it should "return Left on map2" in {
    LEFT.map2(RIGHT) { case (_, right) => right } shouldBe LEFT
  }

  it should "remain Left on flatMap" in {
    LEFT.flatMap(_ => RIGHT) shouldBe LEFT
  }

  it should "return first error when map2 with other Left" in {
    LEFT.map2(OTHER_LEFT)(_ + _) shouldBe LEFT
  }

  "Right" should "apply given function to extracted value" in {
    RIGHT map (_.toString) shouldBe RIGHT_STRING
  }

  it should "return itself on orElse" in {
    RIGHT orElse RIGHT_STRING shouldBe RIGHT
  }

  it should "return first error on map2" in {
    RIGHT.map2(LEFT) { case (right, _) => right } shouldBe LEFT
  }

  it should "apply function when no error is present" in {
    RIGHT.map2(OTHER_RIGHT)(_ + _) shouldBe Right(MAGIC_NUMBER + TWICE_MAGIC_NUMBER)
  }

  it should "become Left if function application fails on flatMap" in {
    RIGHT.flatMap(_ => LEFT) shouldBe LEFT
  }

  it should "return function application on flatMap" in {
    RIGHT.flatMap(i => Right(i + TWICE_MAGIC_NUMBER)) shouldBe Right(MAGIC_NUMBER + TWICE_MAGIC_NUMBER)
  }

  "sequence" should "be Left if any of the arguments is Left" in {
    Either.sequence(List(LEFT, RIGHT, OTHER_LEFT)) shouldBe LEFT
  }

  it should "return list fo extracted values if none of the arguments is Left" in {
    Either.sequence(List(RIGHT, OTHER_RIGHT)) shouldBe Right(List(MAGIC_NUMBER, TWICE_MAGIC_NUMBER))
  }

  "traverse" should "return left is any of the arguments is Left" in {
    Either.traverse(List(RIGHT, LEFT, OTHER_RIGHT))(a => a) shouldBe LEFT
  }

  it should "return list of extracted values with function applied to them if none of the arguments is Left" in {
    Either.traverse(List(RIGHT, OTHER_RIGHT))(a => a.map(_.toString)) shouldBe
      Right(List(MAGIC_NUMBER.toString, TWICE_MAGIC_NUMBER.toString))
  }

}
