package org.skramer.scalalang.expr

import org.skramer.scalalang.`implicit`.ImplicitConversions.AnyOps
import org.skramer.scalalang.layout.Element

/**
  * Based on "Learning scala"
  * Created by skramer on 04.01.17.
  */
class ExprFormatter {
  private val opGroups: Array[Set[String]] =
    Array(
      Set("|", "||"),
      Set("&", "&&"),
      Set("ˆ"),
      Set("==", "!="),
      Set("<", "<=", ">", ">="),
      Set("+", "-"),
      Set("*", "%")
    )

  private val precedence: Map[String, Int] = {
    val assocs =
      for {
        i <- opGroups.indices
        op <- opGroups(i)
      } yield op -> i

    assocs.toMap
  }
  private val unaryPrecedence = opGroups.length
  private val fractionPrecedence = -1

  private def format(expr: Expr, enclPrec: Int): Element = {
    expr match {
      case Var(e) => Element(e)
      case Num(num) =>
        def stripDot(number: String) = if (number.endsWith(".0")) number.substring(0, number.length - 2) else number

        Element(stripDot(num.toString))
      case UnOp(op, arg) => Element(op) beside format(arg, unaryPrecedence)
      case BinOp("/", left, right) =>
        val top = format(left, fractionPrecedence)
        val bottom = format(right, fractionPrecedence)
        val middle = Element('-', top.width max bottom.width, 1)
        val frac = top above middle above bottom

        if (enclPrec !== fractionPrecedence) {
          frac
        }
        else {
          Element(" ") beside frac beside Element(" ")
        }

      case BinOp(op, left, right) =>
        val opPrec = precedence(op)
        val leftElement = format(left, opPrec)
        val rightElement = format(right, opPrec)
        val expr = leftElement beside Element(" " + op + " ") beside rightElement
        if (enclPrec < opPrec) expr else Element("(") beside expr beside Element(")")
    }
  }

  def format(expr: Expr): Element = format(expr, 0)
}
