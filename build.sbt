
name := "minimal-secret-santa"

version := "0.1"

scalaVersion := "2.13.7"

javacOptions ++= Seq("-source", "11", "-target", "11")

libraryDependencies += "info.picocli" % "picocli" % "4.6.1"
libraryDependencies += "org.apache.commons" % "commons-csv" % "1.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.6" % "test"

assembly / mainClass := Some("com.creativeyann17.minimalsecretsanta.Main")
assembly / test := {}

import sbtassembly.AssemblyPlugin.defaultUniversalScript
assembly / assemblyOption := (assembly / assemblyOption).value.copy(prependShellScript = Some(defaultUniversalScript(shebang = false)))
// assembly / assemblyJarName := s"${name.value}.bat" // windows
assembly / assemblyJarName := s"${name.value}"  // mac / linux
//assembly / assemblyJarName := s"${name.value}.jar"  // universal jar, require defaultUniversalScript commented