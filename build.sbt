lazy val commonSettings =
  List(
    organization := "com.ikempf.mower",
    scalaVersion := "2.12.7",
    scalacOptions += "-Ypartial-unification",
    libraryDependencies ++= List(
      "org.typelevel"              %% "cats-core"      % "1.4.0",
      "org.typelevel"              %% "cats-effect"    % "1.0.0",
      "io.chrisdavenport"          %% "log4cats-slf4j" % "0.2.0",
      "com.lihaoyi"                %% "fastparse"      % "2.0.4",
      "ch.qos.logback"             % "logback-classic" % "1.2.3",
      "org.scalatest"              %% "scalatest"      % "3.0.5" % Test,
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6"),
  )

lazy val `mower-functional-abstractions` = (project in file("."))
  .aggregate(`final-tagless`, `free-monad`)

lazy val `common` = (project in file("common"))
  .settings(commonSettings)

lazy val `final-tagless` = (project in file("final-tagless"))
  .settings(commonSettings)
  .dependsOn(`common`)

lazy val `free-monad` = (project in file("free-monad"))
  .settings(commonSettings)
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-free" % "1.5.0"
  )
  .dependsOn(`common`)
