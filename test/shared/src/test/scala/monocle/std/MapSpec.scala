package monocle.std

import monocle.MonocleSuite
import monocle.law.discipline.function._

class MapSpec extends MonocleSuite {
  checkAll("at Map", AtTests[Map[Int, String], Int, Option[String]])
  // typelevel/cats#1831 Map probably does not respect Traverse law at the moment
  // checkAll("each Map", EachTests[Map[Int, String], String])
  checkAll("empty Map", EmptyTests[Map[Int, String]])
  checkAll("index Map", IndexTests[Map[Int, String], Int, String])
  checkAll("filterIndex Map", FilterIndexTests[Map[Int, Char], Int, Char])
}