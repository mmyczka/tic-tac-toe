package pl.tictactoe.core

import cats.{Eq, Show}

enum Player:
  case X
  case O

  def next: Player = this match
    case X => O
    case O => X


object Player:
  given Show[Player] = Show.fromToString
  given Eq[Player] = Eq.fromUniversalEquals

