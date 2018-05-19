package org.skramer.fpis.errorhandling

import org.scalatest.prop.TableDrivenPropertyChecks._
import org.skramer.scalalang.FlatSpecWithMatchers

/**
  *
  *
  * @author created: skramer on 18.05.18 19:38
  */
class OptionTest extends FlatSpecWithMatchers {
  private val MAGIC_NUMBER = 5
  private val MAGIC_NUMBER_STRING = MAGIC_NUMBER.toString
  private val TWICE_MAGIC_NUMBER = 10
  private val TWICE_MAGIC_NUMBER_STRING = TWICE_MAGIC_NUMBER.toString

  "instance of None" should "not change after mapping" in {
    None.map(_.toString) shouldBe None
  }

  it should "return the default value for getOrElse" in {
    None getOrElse MAGIC_NUMBER shouldBe MAGIC_NUMBER
  }
  it should "use provided option for orElse" in {
    None orElse Some(MAGIC_NUMBER) shouldBe Some(MAGIC_NUMBER)
  }

  it should "remain None when flatMapped" in {
    None flatMap (_ => Some(MAGIC_NUMBER)) shouldBe None
  }

  it should "remain None after filtering" in {
    None filter (_ => false) shouldBe None
  }

  "instance of Some" should "change according to map function" in {
    Some(MAGIC_NUMBER).map(_ + 5) shouldBe Some(TWICE_MAGIC_NUMBER)
  }

  it should "extract value from itself for getOrElse" in {
    Some(MAGIC_NUMBER) getOrElse TWICE_MAGIC_NUMBER shouldBe MAGIC_NUMBER
  }

  it should "return itself for orElse" in {
    Some(MAGIC_NUMBER) orElse Some(TWICE_MAGIC_NUMBER) shouldBe Some(MAGIC_NUMBER)
  }

  it should "remain itself when filtered with predicate which evaluates to true" in {
    Some(MAGIC_NUMBER) filter (_ % 2 == 1) shouldBe Some(MAGIC_NUMBER)
  }

  it should "becomeNone when filtered with predicate which evaluates to false" in {
    Some(MAGIC_NUMBER) filter (_ % 2 == 0) shouldBe None
  }

  it should "modify its value when flatMapped" in {
    Some(MAGIC_NUMBER) flatMap (i => Some(i + 5)) shouldBe Some(TWICE_MAGIC_NUMBER)
  }

  it should "become None if flatMap function evaluates to None" in {
    Some(MAGIC_NUMBER) flatMap (_ => None) shouldBe None
  }

  val varianceTestCases = Table(
    ("inputNumbers", "expectedVariance"),
    (List(), None),
    (List(1.0), Some(0)),
    (List(1.0, 2.0), Some(0.25))
  )

  forAll(varianceTestCases) { (numbers, expectedVariance) =>
    Option.variance(numbers) shouldBe expectedVariance
  }

  "lift function" should "allow regular function to work with Optionals" in {
    val serialize: Int => String = _.toString

    val serializeO = Option.lift(serialize)

    serializeO(Some(MAGIC_NUMBER)) shouldBe Some(MAGIC_NUMBER.toString)
  }
  val sum: (Int, Int) => Int = _ + _

  "map2 function" should "return None if any of the arguments is None" in {
    Option.map2(None, None)(sum) shouldBe None
    Option.map2(Some(MAGIC_NUMBER), None)(sum) shouldBe None
    Option.map2(None, Some(MAGIC_NUMBER))(sum) shouldBe None
  }

  it should "apply given function to values extracted from values" in {
    Option.map2(Some(MAGIC_NUMBER), Some(MAGIC_NUMBER))(sum) shouldBe Some(TWICE_MAGIC_NUMBER)
  }
  "sequence function" should "return None if at least one of the arguments is None" in {
    Option.optionSequence(List(Some(MAGIC_NUMBER), None, Some(TWICE_MAGIC_NUMBER), None)) shouldBe None
    Option.optionSequence(List(None, None)) shouldBe None
  }

  it should "return list of extracted values if at none of the arguments is None" in {
    Option.optionSequence(List(Some(MAGIC_NUMBER), Some(TWICE_MAGIC_NUMBER))) shouldBe Some(List(MAGIC_NUMBER, TWICE_MAGIC_NUMBER))
    Option.traverseSequence(List(Some(MAGIC_NUMBER), Some(TWICE_MAGIC_NUMBER))) shouldBe Some(List(MAGIC_NUMBER, TWICE_MAGIC_NUMBER))
  }

  val sumIfEven: Option[Int] => Option[String] = i => i.filter(_ % 2 == 0) map (_.toString)

  "traverse function" should "return None if function applied to any of the arguments return None" in {
    Option.traverse(List(Some(MAGIC_NUMBER), Some(TWICE_MAGIC_NUMBER)))(sumIfEven) shouldBe None
  }

  it should "return  if function applied to any of the arguments return None" in {
    Option.traverse(List(Some(TWICE_MAGIC_NUMBER), Some(TWICE_MAGIC_NUMBER)))(sumIfEven) shouldBe
      Some(List(TWICE_MAGIC_NUMBER_STRING, TWICE_MAGIC_NUMBER_STRING))
  }

}
