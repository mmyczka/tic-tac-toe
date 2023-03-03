package pl.tictactoe.core
import cats.syntax.all._
import cats.Eval
import scala.annotation.tailrec

final case class Game(board: Board, status: GameStatus, moves: Int):
  import Game._
  def move(c: Coordinate, p: Player): Either[GameError, Game] =
    status match
    case GameStatus.Won(winner) => GameError.GameIsOver(winner.some).asLeft
    case GameStatus.Drawn       => GameError.GameIsOver(none).asLeft
    case GameStatus.Ongoing(nextPlayr) =>
      board(c) match
        case Some(FieldStatus.Empty) =>
          if nextPlayr === p then
          board.update(c, FieldStatus.Taken(p))
            .map(updated => process(copy(board = updated, moves = moves + 1), p, c))
            .toRight(GameError.CoordinateOutOfBound(c))
          else GameError.WrongPlayer(p).asLeft
        case Some(FieldStatus.Taken(_)) => GameError.FielsAlreadyTaken(c, p).asLeft
        case None => GameError.CoordinateOutOfBound(c).asLeft


object Game:
  def create: Game = Game(Board.create, GameStatus.Ongoing(Player.X), 0)
  private def process(
    board: Game,
    player: Player,
    coordinate: Coordinate
    ): Game = {
    @tailrec
    def count(direction: Direction, c: Coordinate, score: Int): Int =
      c.translate(direction) match
        case Some(next) => board.board(next) match
          case Some(FieldStatus.Taken(`player`)) =>
            count(direction, next, score + 1)
          case _ => score
        case _ => score

    val victory = List(
      //horizontal
      count(Direction.L, coordinate, 0) + count(Direction.R, coordinate, 0),
      count(Direction.LT, coordinate, 0) + count(Direction.RB, coordinate, 0),
      count(Direction.LB, coordinate, 0) + count(Direction.RT, coordinate, 0),
      count(Direction.B, coordinate, 0) + count(Direction.T, coordinate, 0),
    ).exists(_ + 1 >= BoardSize)

    if victory then board.copy(status = GameStatus.Won(player))
    else if board.moves === (BoardSize * BoardSize) then board.copy(status = GameStatus.Drawn)
    else board.copy(status = GameStatus.Ongoing(player.next))

  }

