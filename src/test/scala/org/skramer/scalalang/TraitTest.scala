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

  "trait" can "be mixed in during object creation" in {
    class Empty
    val e = new Empty with Hello
    e.getValue shouldBe e.MyMagicNumber
  }

  "class with mixin" can "override the method from the trait" in {
    class Empty extends Hello {
      final val NewMagicNumber: Int = 15

      override def getValue: Int = NewMagicNumber
    }

    val e = new Empty
    e.getValue shouldBe e.NewMagicNumber
  }

}
