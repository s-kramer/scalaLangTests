package org.skramer.scalalang

/**
  * Created by skramer on 22.12.16.
  */
class CompositionAndInheritanceTest extends FlatSpecWithMatchers {
  "abstract modifier" can "not be applied to non-concrete methods" in {
    abstract class Foo {
      assertDoesNotCompile(
        """
          |abstract def thisIsAbstract(): Unit
        """.stripMargin)
    }
  }

}
