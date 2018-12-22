package com.ikempf.mower.model

case class Job(mowersAndCommands: List[(Mower, List[Command])]) {
  def mowers: List[Mower] = mowersAndCommands.map(_._1)
}
