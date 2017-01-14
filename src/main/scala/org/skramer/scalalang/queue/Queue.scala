package org.skramer.scalalang.queue

trait Queue[T] {
  def head: T

  def tail: Queue[T]

  def enqueue(newLast: T): Queue[T]
}

trait CovariantQueue[+T] {
  def head: T

  def tail: CovariantQueue[T]

  def enqueue[U >: T](newLast: U): CovariantQueue[U]
}

object Queue {

  /**
    * If all operations are called with the same frequency then this solution has constant asymptotic complexity.
    */
  private class QueueImpl[T] private(private val leading: List[T], private val trailing: List[T]) extends Queue[T] {

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

  private class CovariantQueueImpl[+T](private val leading: List[T], private val trailing: List[T]) extends CovariantQueue[T] {

    def this(queueElements: List[T]) = this(queueElements, Nil)

    private def mirror: CovariantQueueImpl[T] = {
      if (leading.isEmpty) {
        new CovariantQueueImpl(trailing.reverse, Nil) //potentially expensive operation
      } else {
        this
      }
    }

    def head: T = mirror.leading.head

    def tail: CovariantQueue[T] = {
      val mirrored: CovariantQueueImpl[T] = mirror
      new CovariantQueueImpl(mirrored.leading.tail, mirrored.trailing)
    }

    def enqueue[U >: T](newLast: U): CovariantQueue[U] = new CovariantQueueImpl(leading, newLast :: trailing)
  }

  private class QueueWithMutatingMirrorImpl[+T](private[this] var leading: List[T], private[this] var trailing: List[T]) extends CovariantQueue[T] {
    private def mirror(): Unit = {
      if (leading.isEmpty) {
        leading = leading ++ trailing.reverse
      }
    }

    def head: T = {
      mirror()
      leading.head
    }

    def tail: QueueWithMutatingMirrorImpl[T] = {
      mirror()
      new QueueWithMutatingMirrorImpl(leading.tail, trailing)
    }

    def enqueue[U >: T](newLast: U): CovariantQueue[U] = {
      mirror()
      new QueueWithMutatingMirrorImpl[U](leading, newLast :: trailing)
    }
  }

  def apply[T](initialElements: T*): Queue[T] = new QueueImpl[T](initialElements.toList)

  def newCovariantQueue[T](initialElements: T*): CovariantQueue[T] = new CovariantQueueImpl[T](initialElements.toList, Nil)


  def newQueueWithMutatingMirror[T](initialElements: T*): CovariantQueue[T] = new QueueWithMutatingMirrorImpl[T](initialElements.toList, Nil)
}
