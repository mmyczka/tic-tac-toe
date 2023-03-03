package pl.tictactoe.core

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import cats.syntax.all._


class CoordinateTest extends AnyFreeSpec with Matchers{
  "Coordinates should be parse correctly" in {
    val cases = List(
      ("A1", Some(Coordinate(0,0))),
      ("A2", Some(Coordinate(0,1))),
      ("A3", Some(Coordinate(0,2))),
      ("B1", Some(Coordinate(1,0))),
      ("B2", Some(Coordinate(1,1))),
      ("B3", Some(Coordinate(1,2))),
      ("C1", Some(Coordinate(2,0))),
      ("C2", Some(Coordinate(2,1))),
      ("C3", Some(Coordinate(2,2))),
    )

    cases.foreach {
      case (input, expected) =>
        Coordinate.parse(input) shouldBe expected
    }
  }

  "Coordinates should be None" in {
    val cases = List(
      "A0",
      "A4",
      "D1",
      "Z1",
      "1A",
      "B-1",
      "CC3",
      "1A2",
      "A",
      "1",
      ""
    )

    cases.foreach {
      input => Coordinate.parse(input) shouldBe None
    }
  }

  "Coordinates to String" in {
    val cases = List(
      Coordinate(0,0) -> "A1",
      Coordinate(1,1) -> "B2",
      Coordinate(2,2) -> "C3",
      Coordinate(1,0) -> "B1",
      Coordinate(3,4) -> "--OUT OF BOUNDS--",
    )

    cases.foreach {
      case(coordinate, expected) =>
        coordinate.show shouldBe expected
    }
  }

}
