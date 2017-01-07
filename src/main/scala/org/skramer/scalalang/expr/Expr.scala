package org.skramer.scalalang.expr

/**
  * Created by skramer on 04.01.17.
  */
abstract sealed class Expr

final case class Var(name: String) extends Expr

final case class Num(num: Int) extends Expr

final case class UnOp(op: String, arg: Expr) extends Expr

final case class BinOp(op: String, left: Expr, right: Expr) extends Expr
