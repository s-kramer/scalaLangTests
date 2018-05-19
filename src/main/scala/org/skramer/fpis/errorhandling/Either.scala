package org.skramer.fpis.errorhandling

import scala.{Either => _, None => _, Option => _, Some => _}

sealed trait Either[+E, +A] {
  def orElse[EE >: E, B >: A](default: Either[EE, B]): Either[EE, B] = this match {
    case Left(_) => default
    case Right(v) => Right(v)
  }

  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
    case Left(e) => Left(e)
    case Right(v) => f(v)
  }

  def map2[EE >: E, B, C](other: Either[EE, B])(f: (A, B) => C): Either[EE, C] = this match {
    case Left(e) => Left(e)
    case Right(lv) => other.map(rv => f(lv, rv))
  }

  def map[B](f: A => B): Either[E, B] = this match {
    case Left(e) => Left(e)
    case Right(v) => Right(f(v))
  }

  def map22[EE >: E, B, C](other: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
    for {a <- this; b <- other} yield f(a, b)

}

case class Left[E](error: E) extends Either[E, Nothing]

case class Right[A](get: A) extends Either[Nothing, A]

object Either {
  def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]] = traverse(es)(a => a)

  def traverse[E, A, B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] =
    es.foldRight(Right(List()): Either[E, List[B]]) { case (e, acc) => f(e).map2(acc)(_ :: _) }
}

