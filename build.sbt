import Dependencies._

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

val V = new {
  val meowMtlCore = "0.4.0"
}

lazy val root = (project in file("."))
  .settings(
    name := "cats-next-lvl",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      // Classy lenses derivation (requires shapeless)
      "com.olegpy" %% "meow-mtl-core" % V.meowMtlCore,
      // MTL instances for cats-effect Ref and effectful functions
      "com.olegpy" %% "meow-mtl-effects" % V.meowMtlCore
    )
  )
