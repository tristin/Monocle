package monocle.law.discipline.function

import monocle.function.At._
import monocle.function._
import monocle.law.discipline.LensTests
import org.scalacheck.Arbitrary
import org.typelevel.discipline.Laws

import cats.{Eq => Equal}

object AtTests extends Laws {

  def apply[S: Equal : Arbitrary, I: Arbitrary, A: Equal : Arbitrary](implicit evAt: At[S, I, A], arbAA: Arbitrary[A => A]): RuleSet = {
    new SimpleRuleSet("At", LensTests(at(_: I)(evAt)).props: _*)
  }

}