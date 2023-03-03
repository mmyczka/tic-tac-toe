ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file("."))
  .settings(
    name := "tictactoe",
  )

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.4.8"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
