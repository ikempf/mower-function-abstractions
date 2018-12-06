package com.ikempf.mower

import com.ikempf.mower.model.Command.{Advance, TurnLeft, TurnRight}
import com.ikempf.mower.model.Orientation.{East, North}
import com.ikempf.mower.model.{Coordinates, GardenSize, Job, Mower}

object TestData {

  private val mower1    = Mower(Coordinates(1, 2), North)
  private val commands1 = List(TurnLeft, Advance, TurnLeft, Advance, TurnLeft, Advance, TurnLeft, Advance, Advance)
  private val mower2    = Mower(Coordinates(3, 3), East)
  private val commands2 = List(Advance, Advance, TurnRight, Advance, Advance, TurnRight, Advance, TurnRight, TurnRight, Advance)

  val job: Job = Job(List(mower1 -> commands1, mower2 -> commands2))
  val size: GardenSize = GardenSize(5, 5)

}
