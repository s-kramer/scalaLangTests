package org.skramer.scalalang.queue

trait Queue[T] {
  def head: T

  def tail: Queue[T]

  def enqueue(newLast: T): Queue[T]
}

object Queue {

  /**
    * If all operations are called with the same frequency then this solution has constant asymptotic complexity.
    */
  private class QueueImpl[T] private(initialLeading: List[T], initialTrailing: List[T]) extends Queue[T] {
    private val leading = initialLeading
    private val trailing = initialTrailing

    private def mirror: QueueImpl[T] = {
      if (leading.isEmpty) {
        new QueueImpl(trailing.reverse, Nil) //potentially expensive operation
      } else {
        this
      }
    }

    def head: T = mirror.leading.head

    def tail: QueueImpl[T] = {
      val mirrored: QueueImpl[T] = mirror
      new QueueImpl(mirrored.leading.tail, mirrored.trailing)
    }

    def enqueue(newLast: T): QueueImpl[T] = new QueueImpl(leading, newLast :: trailing)
  }

  def apply[T](initialElements: T*): Queue[T] = new QueueImpl[T](initialElements.toList, Nil)
}
