package org.skramer.scalalang.expr

/**
  * Created by skramer on 05.01.17.
  */
object ExprFormatterTester extends App {
  def displayFormatted(expr: Expr): Unit = println((new ExprFormatter).format(expr))

  displayFormatted(
    BinOp("/",

      BinOp("/",
        BinOp("*",
          Var("a"),
          BinOp("+",
            Num(44),
            Num(81))),
        Var("qwerty")),

      BinOp("/",
        Var("a"),
        BinOp("/",
          Var("b"),
          Var("c"))
      )
    )
  )

}
