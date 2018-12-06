package com.ikempf.mower

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.functor._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    Slf4jLogger
      .create[IO]
      .flatMap(implicit log =>
        new JobRunner[IO]().runAll(TestData.size, TestData.job)
      )
      .as(ExitCode.Success)

}
