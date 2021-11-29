
name := "minimal-secret-santa"

version := "0.1"

scalaVersion := "2.13.7"

javacOptions ++= Seq("-source", "11", "-target", "11")

libraryDependencies += "org.apache.commons" % "commons-csv" % "1.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.6" % "test"

assembly / mainClass := Some("Main")
assembly / test := {}
assembly / assemblyJarName := s"${name.value}.jar"