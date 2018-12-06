package com.ikempf.mower.model

sealed trait Orientation

object Orientation {
  case object North extends Orientation
  case object East  extends Orientation
  case object South extends Orientation
  case object West  extends Orientation

  val orientationsCircle: List[Orientation] = List(North, East, South, West)

  def relativeRight(orientation: Orientation): Orientation =
    orientationsCircle.apply((orientationsCircle.indexOf(orientation) + 1) % 4)

  def relativeLeft(orientation: Orientation): Orientation =
    orientationsCircle.apply((orientationsCircle.indexOf(orientation) - 1 + 4) % 4)

}
