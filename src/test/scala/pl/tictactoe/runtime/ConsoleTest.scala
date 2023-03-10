package pl.tictactoe.runtime

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import cats.effect.kernel.Ref
import cats.effect.IO
import org.scalatest.Assertions.fail

class ConsoleTest private(
  inputs: Ref[IO, List[String]],
  outputs: Ref[IO, List[String]]
  )extends Console {
  def printedLines: IO[List[String]] = outputs.get.map(_.reverse)

  override def readLine: IO[String] =
    inputs.modify {
      case x :: xs => (xs, x)
      case Nil => fail("No more input strings for test console!")
    }

  override def printLine(s: String): IO[Unit] = outputs.update(xs => s :: xs)
}
object ConsoleTest {
  def create(inputs: String*): IO[ConsoleTest] = for {
    inputs <- Ref.of[IO, List[String]](inputs.toList)
    outputs <- Ref.of[IO, List[String]](Nil)
  } yield ConsoleTest(inputs, outputs)
}
