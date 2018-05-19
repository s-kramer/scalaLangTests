package org.skramer.fpis.errorhandling

import scala.{Either => _, None => _, Option => _, Some => _}

sealed trait Option[+T] {
  def flatMap[B](f: T => Option[B]): Option[B] = map(f) getOrElse None

  def orElse[B >: T](other: => Option[B]): Option[B] = map(Some(_)) getOrElse other

  def map[B](f: T => B): Option[B] = this match {
    case None => None
    case Some(v) => Some(f(v))
  }

  def getOrElse[B >: T](default: B): B = this match {
    case None => default
    case Some(v) => v
  }

  def filter(p: T => Boolean): Option[T] = this match {
    case Some(v) if p(v) => this
    case _ => None
  }
}

case object None extends Option[Nothing]

final case class Some[+T](get: T) extends Option[T]

object Option {
  def variance(xs: Seq[Double]): Option[Double] = {
    mean(xs).flatMap(m => mean(xs.map(x => Math.pow(x - m, 2))))
  }

  def mean(xs: Seq[Double]): Option[Double] = xs match {
    case Nil => None
    case _ => Some(xs.sum / xs.length)
  }

  def lift[A, B](f: A => B): Option[A] => Option[B] = _ map f

  def optionSequence[A](xs: List[Option[A]]): Option[List[A]] =
    xs.foldRight(Some(List[A]()): Option[List[A]]) { case (acc, e) => map2(acc, e)(_ :: _) }

  def traverseSequence[A](xs: List[Option[A]]): Option[List[A]] = traverse(xs)(a => a)

  def traverse[A, B](xs: List[A])(f: A => Option[B]): Option[List[B]] =
    xs.foldRight(Some(List[B]()): Option[List[B]]) { case (e, acc) => map2(f(e), acc)(_ :: _) }

  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = (a, b) match {
    case (_, None) => None
    case (None, _) => None
    case (Some(x), Some(y)) => Some(f(x, y))
  }

}
