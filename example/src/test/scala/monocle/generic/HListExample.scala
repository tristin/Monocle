package monocle.generic

import monocle.MonocleSuite
import shapeless.HNil

class HListExample extends MonocleSuite {
  case class Example(i: Int, s: String, b: Boolean)

  test("_1 to _6 creates a Lens from HList to ith element") {
    assertEquals((1 :: "bla" :: true :: HNil applyLens first get), 1)
    assertEquals((1 :: "bla" :: true :: HNil applyLens second get), "bla")
    assertEquals((1 :: "bla" :: true :: 5f :: 'c' :: 7L :: HNil applyLens sixth get), 7L)

    assertEquals((1 :: "bla" :: true :: HNil applyLens first modify (_ + 1)), 2 :: "bla" :: true :: HNil)
  }

  test("toHList creates an Iso between a Generic (typically a case class) and HList") {
    assertEquals((Example(1, "bla", true) applyIso toHList get), (1 :: "bla" :: true :: HNil))

    //assertEquals( (Example(1, "bla", true) applyIso toHList applyLens first set 5) ,  Example(5, "bla", true))
  }

  test("reverse creates an Iso between an HList and its reverse version") {
    assertEquals((1 :: "bla" :: true :: HNil applyIso reverse get), (true :: "bla" :: 1 :: HNil))
  }

  test("head creates a Lens from HList to the first element") {
    assertEquals((1 :: "bla" :: true :: HNil applyLens head get), 1)
  }

  test("last creates a Lens from HList to the last element") {
    assertEquals((1 :: "bla" :: true :: HNil applyLens last get), true)
  }

  test("tail creates a Lens from HList to its tail") {
    assertEquals((1 :: "bla" :: true :: HNil applyLens tail get), ("bla" :: true :: HNil))
  }

  test("init creates a Lens from HList to its init") {
    assertEquals((1 :: "bla" :: true :: HNil applyLens init get), (1 :: "bla" :: HNil))
  }
}
