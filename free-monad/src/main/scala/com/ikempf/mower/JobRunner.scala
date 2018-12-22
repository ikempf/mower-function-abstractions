package com.ikempf.mower

import cats.free.Free
import cats.free.Free.liftF
import cats.syntax.apply._
import cats.syntax.functor._
import com.ikempf.mower.Program.{nothing, writeLn}
import com.ikempf.mower.model.Command.{Advance, TurnLeft, TurnRight}
import com.ikempf.mower.model.Orientation.{East, North, South, West, relativeLeft, relativeRight}
import com.ikempf.mower.model.{Command, GardenSize, Job, Mower, Orientation}

sealed trait Program[+A]

object Program {

  case class WriteLn(line: String) extends Program[Unit]
  def writeLn(line: String): Free[Program, Unit] = liftF(WriteLn(line))

}

object JobRunner {

  def runAll(size: GardenSize, job: Job): Free[Program, Unit] =
    new JobRun(size).runAll(job)

  private class JobRun(size: GardenSize) {
    def runAll(job: Job): Free[Program, Unit] =
      writeLn("----Starting job execution----")
        .productR(writeLn("Current mower positions"))
        .productR(printMowerPositions(job.mowers))
        .as(runAll(job.mowersAndCommands))
        .productL(writeLn("----Ended job execution----"))
        .productL(writeLn("Current mower positions"))
        .flatMap(printMowerPositions)

    private def runAll(jobs: List[(Mower, List[Command])]): List[Mower] =
      jobs
        .map((run _).tupled)
        .foldRight(List.empty[Mower])(_ +: _)

    private def run(mower: Mower, commands: List[Command]): Mower =
      commands.foldLeft(mower)(execute)

    private def execute(mower: Mower, command: Command): Mower =
      command match {
        case Advance => val (x, y) = orientationVector(mower.orientation)
          val newX = horizontalLimit(mower.coordinates.x + x)
          val newY = verticalLimit(mower.coordinates.y + y)
          mower.copy(coordinates = mower.coordinates.copy(x = newX, y = newY))
        case TurnLeft => mower.copy(orientation = relativeLeft(mower.orientation))
        case TurnRight => mower.copy(orientation = relativeRight(mower.orientation))
      }

    private def horizontalLimit(x: Int): Int =
      Math.max(0, Math.min(x, size.width))

    private def verticalLimit(x: Int): Int =
      Math.max(0, Math.min(x, size.height))

    private def orientationVector(orientation: Orientation): (Int, Int) =
      orientation match {
        case North => (0, 1)
        case East => (1, 0)
        case South => (0, -1)
        case West => (-1, 0)
      }

    private def printMowerPositions(mowers: List[Mower]): Free[Program, Unit] =
      mowers
        .map(Show.show)
        .map(writeLn)
        .fold(writeLn(""))((a, b) => a.productR(b))

  }

}
