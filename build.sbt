lazy val root = (project in file (".")).
  settings(
    name := "hello",
    version := "1.0",
    scalaVersion := "2.11.8"
  )

libraryDependencies ++= {
  val version = "2.4.11"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % version,
    "com.typesafe.akka" %% "akka-stream" % version
  )
}