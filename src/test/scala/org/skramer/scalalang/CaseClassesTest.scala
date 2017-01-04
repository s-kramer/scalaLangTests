package org.skramer.scalalang

import org.skramer.scalalang.expr._

/**
  * Created by skramer on 30.12.16.
  */
class CaseClassesTest extends FlatSpecWithMatchers {
  "case classes" should "get a factory method, fields and method implementations by default" in {
    case class Foo(value: Int)
    val first, second = Foo(5)
    first.value should be(5)
    second.value should be(5)
    assert(first == second)
    first.toString shouldBe "Foo(5)"
  }

  "case classes" can "be copied with partial modifications" in {
    case class Foo(val1: Int, val2: Int)
    val first = Foo(5, 10)
    val second = first.copy(val2 = 15)

    first.val1 shouldBe 5
    first.val2 shouldBe 10
    second.val1 shouldBe 5
    second.val2 shouldBe 15
  }

  "case classes" can "be pattern matched" in {
    case class Foo(v: Int, k: String)
    Foo(5, "bar") match {
      case Foo(v, "bar") => v shouldBe 5
      case _ => fail()
    }
  }

  "pattern matching" should "throw if none of the patterns matched" in {
    assertThrows[MatchError](
      5 match {
        case 11 => succeed
      }
    )
  }

  "match" should "be an expression and return a value" in {
    val result = "foo" match {
      case "foo" => 15
    }

    result shouldBe 15
  }

  "pattern matching" should "recognize constants by uppercase" in {
    import math.Pi
    val result = 3.14 match {
      case Pi => "pi"
      case pi => "not a pi"
    }

    result shouldBe "not a pi"
  }

  "pattern matching" can "enforce lowercase to be recognized as constant by backtics" in {
    import math.{Pi => pi}
    val result = math.Pi match {
      case `pi` => "pi"
      case notAPi => "not a pi"
    }

    result shouldBe "pi"
  }

  "pattern matching" should "support deep matching" in {

    def threeLevelDeepMatch(expr: Expr): Boolean = expr match {
      case BinOp(_, _, Num(0)) => true
      case _ => false
    }

    threeLevelDeepMatch(BinOp("", Var("variable"), Num(0))) should be(true)
  }

  "pattern matching" can "match against sequences" in {
    List(1, 2, 3) match {
      case List(5, _* /*match any number*/) => fail
      case List(1, _, 3) => succeed
      case _ => fail
    }
  }

  "pattern matching" can "match against a tupple" in {
    (1, 2, 3) match {
      case (_, _, 5) => fail
      case (_, _, _) => succeed
    }
  }

  "pattern matching" can "perform typed matching" in {
    val b: Expr = BinOp("op", Num(1), Var("variable"))
    b match {
      case n: Num => fail
      case v: Var => fail
      case u: UnOp => fail
      case b: BinOp => succeed
    }
  }

  "pattern matching" can "extract and assign fields of a tuple" in {
    def returnATupple = (1, 2)

    val (first, second) = returnATupple
    first shouldBe 1
    second shouldBe 2

    val surprisingFirst, surprisingSecond = returnATupple
    surprisingFirst shouldBe(1, 2)
    surprisingSecond shouldBe(1, 2)
  }

  "elements of a case class" can "be extracted" in {
    val BinOp(op, v1, v2) = BinOp("op", Num(1), Num(2))
    op shouldBe "op"
    v1 shouldBe Num(1)
    v2 shouldBe Num(2)
  }

  "pattern matching" can "suffer from type erasure" in {
    def matchTypedArgument(arg: Any): Option[String] = arg match {
      case mapWithStrings: Map[String, String] => Some("Map[String, String]")
      case mapWithInts: Map[Int, Int] => Some("Map[Int, Int]")
      case _ => None
    }

    matchTypedArgument(Map(1 -> 1)) shouldBe Some("Map[String, String]") // :(
  }

  "pattern matching" can "bind variables to partial matches" in {
    val b = BinOp("op", Num(1), Num(2))
    val result = (b: @unchecked) match {
      case BinOp(_, e@Num(_), _) => e
    }

    result shouldBe Num(1)
  }

  "pattern matching" can "have pattern guards" in {
    Num(15) match {
      case Num(v) if v == 1 => fail
      case Num(v) if v >= 15 => succeed
    }

    BinOp("op", Num(1), Num(1)) match {
      //      case BinOp(_, x, x) => succeed // This can't work
      case BinOp(_, x, y) if x == y => succeed // this matches only if two operands are equal
    }
  }

  "pattern matching" should "produce warnings on non-exhaustive matches of sealed class" in {
    def matchAnyExpr(expr: Expr) = (expr: @unchecked) match {
      case Var(_) => fail
    }
  }

  "pattern matching" can "be queried for argument support with partial functions" in {
    val partiallyApplicableFunction: List[Int] => Int = {
      case _ :: y :: _ => y
    }
    partiallyApplicableFunction(List(1, 2, 3)) shouldBe 2
    assertThrows[MatchError](partiallyApplicableFunction(Nil))

    val partialFunction: PartialFunction[List[Int], Int] = {
      case _ :: y :: _ => y
    }
    partialFunction.isDefinedAt(List(1, 2, 3)) shouldBe true
    partialFunction.isDefinedAt(Nil) shouldBe false
  }

  "for expression with pattern matching" should "filter out non-matching elements" in {
    val values = List(Some(1), None, Some(3), None, Some(15))

    (for (Some(i) <- values) yield i) should be(1 :: 3 :: 15 :: Nil)
  }

  "for expression with pattern matching" should "filter out non-matching elements 2" in {
    val values = List(BinOp("op", Num(1), Num(2)), Num(1), Var("aaa"))

    (for (Var(i) <- values) yield i) should be("aaa" :: Nil)
  }

}

