package pl.tictactoe.core

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import cats.syntax.all._
class GameTest extends AnyFreeSpec with Matchers{

  extension(b: Game)
    def moveMultiple(moves: (String, Player)*):Either[GameError, Game] =
      moves.foldLeft(b.asRight) {
        case(board, (move, player)) =>
          val coordinate = Coordinate.parse(move).getOrElse(fail(s"Wrong coordinates: $move !"))
          board.flatMap(_.move(coordinate, player))
      }



  "Should allow playing whole game and drawing" in {
    val board  = Game.create

    val Right(result) = board.moveMultiple(
      ("A1", Player.X),
      ("A2", Player.O),
      ("A3", Player.X),
      ("B1", Player.O),
      ("B3", Player.X),
      ("B2", Player.O),
      ("C2", Player.X),
      ("C3", Player.O),
      ("C1", Player.X)
    )

    result.status shouldBe GameStatus.Drawn
  }

}
