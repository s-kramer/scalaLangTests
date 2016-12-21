package org.skramer.scalalang.rational

/**
  * Created by skramer on 21.12.16.
  */
class Rational(nominator: Int, denominator: Int) {
  require(denominator != 0)

  override def toString: String = s"$nominator / $denominator"
}
