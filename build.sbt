import AssemblyKeys._

assemblySettings

jarName in assembly := "bitcoinServer.jar"

name := "bitcoinServer"

version := "1.0"

scalaVersion := "2.11.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.3.6"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-remote" % "2.3.6"
