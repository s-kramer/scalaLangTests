package org.skramer.scalalang

/**
  * Created by skramer on 28.12.16.
  */
trait Hello {
  private[scalalang] final val MyMagicNumber: Int = 5

  def getValue: Int = MyMagicNumber
}

class TraitTest extends FlatSpecWithMatchers {
  "trait" can "be mixed into class" in {

    val h = new Hello {}
    class Empty extends Hello
    // todo: why an object has to be created here? 
    // TODO: move magic number definition to class that mixes in the trait
    (new Empty).getValue shouldBe h.MyMagicNumber
  }

}
