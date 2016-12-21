package org.skramer.scalalang.rational

/**
  * Created by skramer on 21.12.16.
  */
class Rational(val nominator: Int, val denominator: Int) {
  require(denominator != 0)

  override def toString: String = s"$nominator / $denominator"

  def add(other: Rational): Rational = {
    new Rational(nominator * other.denominator + other.nominator * denominator, denominator * other.denominator)
  }
}
