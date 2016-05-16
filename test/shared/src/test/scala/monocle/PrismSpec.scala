package monocle

import monocle.law.discipline.{OptionalTests, PrismTests, SetterTests, TraversalTests}

import scalaz._
import scalaz.std.list._

class PrismSpec extends MonocleSuite {

  def _right[E, A]: Prism[E \/ A, A] = Prism[E \/ A, A](_.toOption)(\/.right)

  val _nullary: Prism[Arities, Unit] =
    Prism[Arities, Unit] {
      case Nullary() => Some(())
      case _         => None
    } {
      case () => Nullary()
    }
  val _unary: Prism[Arities, Int] =
    Prism[Arities, Int] {
      case Unary(i) => Some(i)
      case _        => None
    } (Unary)
  val _binary: Prism[Arities, (String, Int)] =
    Prism[Arities, (String, Int)] {
      case Binary(s, i) => Some((s, i))
      case _            => None
    } (Binary.tupled)
  val _quintary: Prism[Arities, (Any, Boolean, String, Int, Double)] =
    Prism[Arities, (Any, Boolean, String, Int, Double)] {
      case Quintary(a, b, s, i, f) => Some((a, b, s, i, f))
      case _                       => None
    } (Quintary.tupled)


  checkAll("apply Prism", PrismTests(_right[String, Int]))

  checkAll("prism.asTraversal", OptionalTests(_right[String, Int].asOptional))
  checkAll("prism.asTraversal", TraversalTests(_right[String, Int].asTraversal))
  checkAll("prism.asSetter"   , SetterTests(_right[String, Int].asSetter))

  checkAll("first" , PrismTests(_right[String, Int].first[Boolean]))
  checkAll("second", PrismTests(_right[String, Int].second[Boolean]))
  checkAll("left"  , PrismTests(_right[String, Int].left[Boolean]))
  checkAll("right" , PrismTests(_right[String, Int].right[Boolean]))

  // test implicit resolution of type classes

  test("Prism has a Compose instance") {
    Compose[Prism].compose(_right[String, Int], _right[String, String \/ Int]).getOption(\/-(\/-(3))) shouldEqual Some(3)
  }

  test("Prism has a Category instance") {
    Category[Prism].id[Int].getOption(3) shouldEqual Some(3)
  }

  test("only") {
    Prism.only(5).getOption(5) shouldEqual Some(())
  }

  test("below") {
    val _5s = Prism.only(5).below[List]
    _5s.getOption(List(1,2,3,4,5)) shouldEqual None
    _5s.getOption(List(5,5,5))     shouldEqual Some(List((), (), ()))
  }

  test("apply") {
    _nullary() shouldEqual Nullary()
    _unary(3) shouldEqual Unary(3)
    _binary("foo", 7) shouldEqual Binary("foo", 7)
    _quintary((), true, "bar", 13, 0.4) shouldEqual
      Quintary((), true, "bar", 13, 0.4)
  }
}