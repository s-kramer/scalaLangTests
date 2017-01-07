package org.skramer.scalalang.queue

trait Queue[T] {
  def head: T

  def tail: Queue[T]

  def enqueue(newLast: T): Queue[T]
}

trait QueueWithLowerBound[T] extends Queue[T] {
  def enqueueWithLower[U >: T](newLast: U): QueueWithLowerBound[U]
}

object Queue {

  /**
    * If all operations are called with the same frequency then this solution has constant asymptotic complexity.
    */
  private class QueueImpl[T] private[queue](private val leading: List[T], private val trailing: List[T]) extends Queue[T] {

    def this(queueElements: List[T]) = this(queueElements, Nil)

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

  private class QueueWithLowerBoundImpl[T](leading: List[T], trailing: List[T])
    extends QueueImpl[T](leading, trailing) with QueueWithLowerBound[T] {

    override def enqueueWithLower[U >: T](newLast: U): QueueWithLowerBoundImpl[U] = new QueueWithLowerBoundImpl[U](leading, newLast :: trailing)
  }

  def apply[T](initialElements: T*): Queue[T] = new QueueImpl[T](initialElements.toList)

  def newQueueWithLowerBound[T](initialElements: T*): QueueWithLowerBound[T] = new QueueWithLowerBoundImpl[T](initialElements.toList, Nil)
}
