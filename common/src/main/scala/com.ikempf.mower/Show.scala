package com.ikempf.mower

import com.ikempf.mower.model.{Mower, Orientation}
import com.ikempf.mower.model.Orientation.{East, North, South, West}
import cats.syntax.show._
import cats.instances.int._
import cats.instances.string._

object Show {

  def show(mower: Mower): String =
    show"${mower.coordinates.x} ${mower.coordinates.y} ${show(mower.orientation)}"

  private def show(orientation: Orientation): String =
    orientation match {
      case North => "N"
      case East  => "E"
      case South => "S"
      case West  => "W"
    }

}
