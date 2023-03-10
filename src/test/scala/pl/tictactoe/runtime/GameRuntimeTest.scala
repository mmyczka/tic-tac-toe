package pl.tictactoe.runtime

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import cats.effect.IO
import pl.tictactoe.core.Game
import pl.tictactoe.runtime.GameRuntime
import cats.syntax.all._

class GameRuntimeTest extends AnyFreeSpec with Matchers{
  "Player X wins" in {
    val VictoryX = List(
      "A1",
      "B1",
      "A2",
      "B2",
      "A3"
    )

    for {
      console <- ConsoleTest.create(VictoryX*)
      _ <- GameRuntime(console).run
      lines <- console.printedLines
    }yield lines.lastOption contains("\nPlayer X won the game!\n")
  }
}
