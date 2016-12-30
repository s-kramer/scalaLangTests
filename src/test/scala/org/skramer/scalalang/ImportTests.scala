package org.skramer.scalalang


class Foo {
  def bar: Int = 5

  def baz: Int = 15
}

/**
  * Created by skramer on 30.12.16.
  */
class ImportTests extends FlatSpecWithMatchers {
  "all members of a class" can "be imported" in {

    def checkFoo(f: Foo): Unit = {
      import f._
      bar shouldBe 5
      baz shouldBe 15
    }

    checkFoo(new Foo)
  }

  "import" can "rename member" in {
    def checkFoo(f: Foo) = {
      import f.{bar => first, baz => second}
      first shouldBe 5
      second shouldBe 15
    }

    checkFoo(new Foo)
  }

  "specific imports" can "be excluded from a batch import" in {
    def checkFoo(f: Foo) = {
      import f.{baz => _, _}
      bar shouldBe 5
      assertDoesNotCompile(
        """
          |val i = baz
        """.stripMargin)
    }

    checkFoo(new Foo)

  }
}
