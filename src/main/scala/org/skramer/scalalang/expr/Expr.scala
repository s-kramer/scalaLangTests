package org.skramer.scalalang.expr

/**
  * Created by skramer on 04.01.17.
  */
abstract sealed class Expr

case class Var(name: String) extends Expr

case class Num(num: Int) extends Expr

case class UnOp(op: String, arg: Expr) extends Expr

case class BinOp(op: String, left: Expr, right: Expr) extends Expr
