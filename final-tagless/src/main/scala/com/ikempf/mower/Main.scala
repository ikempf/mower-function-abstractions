package com.ikempf.mower

import cats.effect.{ExitCode, IO, IOApp}
import com.ikempf.mower.Command.{Advance, TurnLeft, TurnRight}
import com.ikempf.mower.Orientation.{East, North}
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import cats.syntax.functor._
import cats.syntax.apply._
import cats.syntax.monad._

object Main extends IOApp {

  val mower1    = Mower(Coordinates(1, 2), North)
  val commands1 = List(TurnLeft, Advance, TurnLeft, Advance, TurnLeft, Advance, TurnLeft, Advance, Advance)
  val mower2    = Mower(Coordinates(3, 3), East)
  val commands2 = List(Advance, Advance, TurnRight, Advance, Advance, TurnRight, Advance, TurnRight, TurnRight, Advance)

  val job = List(mower1 -> commands1, mower2 -> commands2)

  override def run(args: List[String]): IO[ExitCode] =
    Slf4jLogger
      .create[IO]
      .flatMap(log => {
        implicit val t = log
        new Garden[IO](5, 5).runAll(job)
      })
      .as(ExitCode.Success)
}
