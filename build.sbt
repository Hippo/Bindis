lazy val root = project
  .in(file("."))
  .settings(
    name := "Bindis",
    description := "A binary file disassembly library for the JVM.",
    version := "1.0.0",
    scalaVersion := "3.0.1",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
)