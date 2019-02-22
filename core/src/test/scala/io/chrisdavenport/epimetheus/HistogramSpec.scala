package io.chrisdavenport.epimetheus

import cats.effect._
import org.specs2.mutable.Specification
import cats.effect.laws.util.TestContext
import shapeless._

class HistogramSpec extends Specification {
  "Histogram No Labels" should {
    "Register cleanly in the collector" in {
      implicit val ec = TestContext()
      implicit val T = ec.timer[IO]

      val test = for {
        cr <- CollectorRegistry.build[IO]
        h <- Histogram.buildBuckets[IO](cr, "boo", "Boo ", 0.1, 0.2, 0.3, 0.4)
      } yield h

      test.attempt.unsafeRunSync must beRight
    }
  }

  "Histogram Labelled" should {
    "Register cleanly in the collector" in {
      implicit val ec = TestContext()
      implicit val T = ec.timer[IO]

      val test = for {
        cr <- CollectorRegistry.build[IO]
        h <- Histogram.construct(cr, "boo", "Boo ", Sized("boo"), {s: String => Sized(s)}, 0.1, 0.2, 0.3, 0.4)
      } yield h

      test.attempt.unsafeRunSync must beRight
    }
  }
}