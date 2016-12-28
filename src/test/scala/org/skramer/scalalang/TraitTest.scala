package org.skramer.scalalang

/**
  * Created by skramer on 28.12.16.
  */
trait Hello {
  val MyMagicNumber: Int = Hello.MyMagicNumber
}

// TODO: suppress findbugs false positives
object Hello {
  val MyMagicNumber: Int = 5
}

class TraitTest extends FlatSpecWithMatchers {
  "trait" can "be mixed into class" in {

    val h = new Hello {}
    class Empty extends Hello
    // TODO: move magic number definition to class that mixes in the trait
    (new Empty).MyMagicNumber shouldBe h.MyMagicNumber
  }

  "trait" can "be mixed in during object creation" in {
    class Empty
    val e = new Empty with Hello
    e.MyMagicNumber shouldBe Hello.MyMagicNumber
  }

  "class with mixin" can "override the method from the trait" in {
    class Empty extends Hello {
      // note: val must be used here to override, you cannot override a val with a def
      override val MyMagicNumber: Int = Empty.NewMagicNumber
    }

    object Empty {
      final val NewMagicNumber: Int = 15
    }

    val e = new Empty
    e.MyMagicNumber shouldBe Empty.NewMagicNumber
  }

}
