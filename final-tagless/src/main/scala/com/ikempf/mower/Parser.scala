package com.ikempf.mower

import com.ikempf.mower.model.Command.{Advance, TurnLeft, TurnRight}
import com.ikempf.mower.model.Orientation.{East, North, South, West}
import com.ikempf.mower.model._
import fastparse._
import NoWhitespace._

object Parser {

  /**
    * 5 5
    * 1 2 N
    * GAGAGAGAA
    * 3 3 E
    * AADAADADDA
    */
  def gardenAndJob[_: P]: P[(GardenSize, Job)] = P(gardenSize ~ breakLine ~ job)

  def job[_: P]: P[Job] = (mowerWithCommand ~ breakLine).rep(1).map(_.toList).map(Job.apply)

  def mowerWithCommand[_: P]: P[(Mower, List[Command])] = P(mower ~ breakLine ~ commands)
  def mower[_: P]: P[Mower] = P(coordinates ~ orientation).map((Mower.apply _).tupled)
  def commands[_: P]: P[List[Command]] = command.rep(1).map(_.toList)

  def command[_: P]: P[Command] = P(CharIn("AGD")).!.map {
    case "A" => Advance
    case "G" => TurnLeft
    case "D" => TurnRight
  }

  def orientation[_: P]: P[Orientation] = P(CharIn("NSEW")).!.map {
    case "N" => North
    case "E" => East
    case "S" => South
    case "W" => West
  }

  def coordinates[_: P]: P[Coordinates] = tuple.map((Coordinates.apply _).tupled)
  def gardenSize[_: P]: P[GardenSize] = tuple.map((GardenSize.apply _).tupled)
  def tuple[_: P]: P[(Int, Int)] = P(num ~ space ~ num)

  def num[_: P]: P[Int] = P(CharIn("[0-9]").rep(1)).!.map(_.toInt)
  def space[_: P]: P[Unit] = P(" ")
  def breakLine[_: P]: P[Unit] = P("\n")

}