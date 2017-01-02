package org.skramer.scalalang

/**
  * Created by skramer on 02.01.17.
  */
class MutableObjectsTest extends FlatSpecWithMatchers {

  "all class fields" can "have custom getters and setters" in {
    var wasGetterCalled = false
    var wasSetterCalled = false

    class Foo(bar: Int) {

      private[this] var v: Int = bar

      def value: Int = {
        wasGetterCalled = true
        v
      }

      def value_=(x: Int): Unit = {
        wasSetterCalled = true
        v = x
      }
    }

    val f = new Foo(15)
    assert(!wasGetterCalled)
    assert(!wasSetterCalled)

    f.value = 20
    assert(!wasGetterCalled)
    assert(wasSetterCalled)

    f.value shouldBe 20
    assert(wasGetterCalled)
    assert(wasSetterCalled)
  }
}
