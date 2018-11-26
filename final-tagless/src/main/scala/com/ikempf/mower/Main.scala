package com.ikempf.mower

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.functor._
import com.ikempf.mower.domain.JobRunner
import com.ikempf.mower.domain.model.Command.{Advance, TurnLeft, TurnRight}
import com.ikempf.mower.domain.model.Orientation.{East, North}
import com.ikempf.mower.domain.model.{Coordinates, GardenSize, Mower}
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

object Main extends IOApp {

  val mower1    = Mower(Coordinates(1, 2), North)
  val commands1 = List(TurnLeft, Advance, TurnLeft, Advance, TurnLeft, Advance, TurnLeft, Advance, Advance)
  val mower2    = Mower(Coordinates(3, 3), East)
  val commands2 = List(Advance, Advance, TurnRight, Advance, Advance, TurnRight, Advance, TurnRight, TurnRight, Advance)

  val job  = List(mower1 -> commands1, mower2 -> commands2)
  val size = GardenSize(5, 5)

  override def run(args: List[String]): IO[ExitCode] =
    Slf4jLogger
      .create[IO]
      .flatMap(implicit log =>
        new JobRunner[IO](size).runAll(job)
      )
      .as(ExitCode.Success)
}
