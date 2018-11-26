package com.ikempf.mower.domain.model

sealed trait Command

object Command {
  case object Advance   extends Command
  case object TurnLeft  extends Command
  case object TurnRight extends Command
}
