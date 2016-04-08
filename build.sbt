name := "akka-sample-http-stream"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-stream" % "2.4.3",
	"org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5",
  "org.json4s" %% "json4s-native" % "3.3.0"
)