package com.ikempf.mower

import cats.{Id, ~>}
import com.ikempf.mower.Program.{Nothing, WriteLn}


object Main extends App {

  val program = JobRunner.runAll(TestData.size, TestData.job)

  program.foldMap(impureCompiler)

  def impureCompiler: Program ~> Id  =
    new (Program ~> Id) {
      def apply[A](fa: Program[A]): Id[A] =
        fa match {
          case WriteLn(line) =>
            println(line)
            ()
        }
    }


}
