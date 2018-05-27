package org.skramer.fpis.laziness


/**
  *
  *
  * @author created: skramer on 19.05.18 14:34
  */
sealed trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, _) => Some(h())
  }

  def toList: List[A] = this match {
    case Empty => List()
    case Cons(h, tail) => h() :: tail().toList
  }

  def take(n: Int): Stream[A] = this match {
    case Empty => Empty
    case Cons(_, _) if n <= 0 => Empty
    case Cons(h, t) => Stream.cons(h(), t().take(n - 1))
  }

  def drop(n: Int): Stream[A] = this match {
    case Empty => Empty
    case s@Cons(_, _) if n <= 0 => s
    case Cons(h, t) => t().drop(n - 1)
  }

  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Empty => Empty
    case Cons(h, _) if !p(h()) => Empty
    case Cons(h, t) => Stream.cons(h(), t().takeWhile(p))
  }

  def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
    case Cons(h, t) => f(h(), t().foldRight(z)(f))
    case _ => z
  }

  def exists(p: A => Boolean): Boolean = foldRight(false)((e, acc) => p(e) || acc)

  def existsRec(p: A => Boolean): Boolean = this match {
    case Empty => false
    case Cons(h, t) => p(h()) || t().existsRec(p)
  }
}

case object Empty extends Stream[Nothing]

case class Cons[A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def empty: Stream[Nothing] = Empty

  def tabulate[A](n: Int)(f: Int => A): Stream[A] = Stream.cons(f(n), tabulate(n + 1)(f))

  def cons[A](h: => A, t: => Stream[A]): Stream[A] = {
    lazy val head = h
    lazy val tail = t
    Cons(() => head, () => tail)
  }

  def apply[A](as: A*): Stream[A] = if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))

}
