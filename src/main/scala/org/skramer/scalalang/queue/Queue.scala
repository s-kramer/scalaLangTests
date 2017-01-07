package org.skramer.scalalang.queue

/**
  * Created by skramer on 07.01.17.
  */
class Queue[T] private(initialLeading: List[T], initialTrailing: List[T]) {
  private val leading = initialLeading
  private val trailing = initialTrailing

  def this(leading: List[T]) = this(leading, Nil)

  private def mirror: Queue[T] = {
    if (leading.isEmpty) {
      new Queue(trailing.reverse, Nil)
    } else {
      this
    }
  }

  def head: T = mirror.leading.head

  def tail: Queue[T] = {
    val mirrored: Queue[T] = mirror
    new Queue(mirrored.leading.tail, mirrored.trailing)
  }

  def enqueue(newLast: T): Queue[T] = new Queue(leading, newLast :: trailing)
}
