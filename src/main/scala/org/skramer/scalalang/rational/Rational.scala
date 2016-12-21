package org.skramer.scalalang.rational

/**
  * Created by skramer on 21.12.16.
  */
class Rational(nom: Int, denom: Int) {
  require(denom != 0)
  private val currentGcd = gcd(nom, denom)
  val nominator: Int = nom / currentGcd
  val denominator: Int = denom / currentGcd

  def this(nominator: Int) = this(nominator, 1)

  override def toString: String = s"$nominator / $denominator"

  def +(other: Rational): Rational = {
    new Rational(nominator * other.denominator + other.nominator * denominator, denominator * other.denominator)
  }

  def *(other: Rational): Rational = {
    new Rational(nominator * other.nominator, denominator * other.denominator)
  }

  private def gcd(a: Int, b: Int): Int = if (b == 0) a.abs else gcd(b, a % b)
}
