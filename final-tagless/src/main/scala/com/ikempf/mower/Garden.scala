package com.ikempf.mower

import cats.Monad
import cats.syntax.apply._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.show._
import cats.syntax.traverse._
import cats.instances.string._
import cats.instances.int._
import cats.instances.list._
import com.ikempf.mower.Command.{Advance, TurnLeft, TurnRight}
import com.ikempf.mower.Orientation.{East, North, South, West, relativeLeft, relativeRight}
import io.chrisdavenport.log4cats.Logger

class Garden[F[_]: Logger: Monad](width: Int, height: Int) {

  def runAll(mowers: List[(Mower, List[Command])]): F[List[Mower]] =
    Logger[F]
      .info("Executing instructions")
      .as(mowers.map((run _).tupled))
      .productL(Logger[F].info("Finished executing instructions"))
      .flatTap(mowers =>
        Logger[F]
          .info(show"---Final positions---")
          .productR(mowers.traverse(mower => Logger[F].info(show(mower))))
      )

  private def run(mower: Mower, commands: List[Command]): Mower =
    commands.foldLeft(mower)(execute)

  private def execute(mower: Mower, command: Command): Mower =
    command match {
      case TurnLeft  => mower.copy(orientation = relativeLeft(mower.orientation))
      case TurnRight => mower.copy(orientation = relativeRight(mower.orientation))
      case Advance =>
        val (x, y) = orientationVector(mower.orientation)
        val newX   = horizontalLimit(mower.coordinates.x + x)
        val newY   = verticalLimit(mower.coordinates.y + y)
        mower.copy(coordinates = mower.coordinates.copy(x = newX, y = newY))
    }

  private def horizontalLimit(x: Int): Int =
    Math.max(0, Math.min(x, width))

  private def verticalLimit(x: Int): Int =
    Math.max(0, Math.min(x, height))

  private def orientationVector(orientation: Orientation): (Int, Int) =
    orientation match {
      case North => (0, 1)
      case East  => (1, 0)
      case South => (0, -1)
      case West  => (-1, 0)
    }

  private def show(mower: Mower): String =
    show"${mower.coordinates.x} ${mower.coordinates.y} ${show(mower.orientation)}"

  private def show(orientation: Orientation) =
    orientation match {
      case North => "N"
      case East  => "E"
      case South => "S"
      case West  => "W"
    }

}
