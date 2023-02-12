import com.typesafe.sbt.packager.docker.Cmd

ThisBuild / scalaVersion     := "2.13.5"
ThisBuild / organization     := "com.intuit"
ThisBuild / organizationName := "intuit"

version := "1.0"

val AkkaVersion = "2.6.14"
val JacksonVersion = "2.11.4"

lazy val root = (project in file("."))
  .settings(
    name := "intuit-payments-rest",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      "org.mockito" % "mockito-core" % "3.11.2" % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "io.prometheus" % "simpleclient" % "0.12.0",
      "io.prometheus" % "simpleclient_hotspot" % "0.12.0",
      "io.prometheus" % "simpleclient_httpserver" % "0.12.0",
      "com.github.pureconfig" %% "pureconfig" % "0.11.1",
      "com.typesafe.akka" %% "akka-stream-kafka" % "2.1.0",
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.fasterxml.jackson.core" % "jackson-databind" % JacksonVersion,
      "com.google.inject" % "guice" % "5.1.0",
      "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0",
      ehcache , ws, guice
    )
  ).enablePlugins(PlayScala).enablePlugins(DockerPlugin)

Docker / version := "latest"
Docker / packageName := name.value
Docker / maintainer := "we@maintian.com"
Docker / daemonUser := "root"
dockerBaseImage := "openjdk:13-jdk-alpine"
dockerExposedPorts := Seq(9000)
dockerUpdateLatest := true

dockerCommands ++= Seq(
  Cmd("USER", "root"),
  Cmd("RUN", "apk add --update bash && rm -rf /var/cache/apk/*")
)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      