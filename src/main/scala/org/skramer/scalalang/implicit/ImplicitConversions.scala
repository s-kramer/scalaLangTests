package org.skramer.scalalang.`implicit`

/**
  * Type safe comparison
  * Created by skramer on 06.01.17.
  */
object ImplicitConversions {

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  implicit final class AnyOps[A](self: A) {
    def ===(other: A): Boolean = self == other

    def !==(other: A): Boolean = self != other
  }

}
