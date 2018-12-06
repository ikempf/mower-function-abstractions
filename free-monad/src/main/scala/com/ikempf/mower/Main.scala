package com.ikempf.mower

sealed trait Program

case class PrintLn(line: String)

object Main extends App {


  JobRunner.runAll(TestData.job)


}
