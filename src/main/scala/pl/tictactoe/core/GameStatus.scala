package pl.tictactoe.core

enum GameStatus:
  case Ongoing(nextPlayr: Player)
  case Won(winner: Player)
  case Drawn
