package org.skramer.scalalang

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
    class Expr
    case class Var(name: String) extends Expr
    case class Number(num: Int) extends Expr
    case class UnOp(op: String, arg: Expr) extends Expr
    case class BinOp(op: String, left: Expr, right: Expr) extends Expr

    def threeLevelDeepMatch(expr: Expr): Boolean = expr match {
      case BinOp(_, _, Number(0)) => true
      case _ => false
    }

    threeLevelDeepMatch(BinOp("", Var("variable"), Number(0))) should be(true)
  }

}
