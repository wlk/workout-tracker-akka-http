import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform

enablePlugins(JavaAppPackaging)

name := "workout-tracker-akka-http"
organization := "com.wlangiewicz"
version := "1.0"
scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"

libraryDependencies ++= {
  val akkaVersion       = "2.4.2"
  val akkaStreamVersion = "2.0.3"
  val scalaTestVersion  = "3.0.0-M15"
  val nscalaTimeVersion = "2.10.0"

  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamVersion,
    "com.github.nscala-time" %% "nscala-time"                     % nscalaTimeVersion,
    "org.scalatest"     %% "scalatest"                            % scalaTestVersion % "test"
  )
}

SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(SpacesAroundMultiImports, false)
  .setPreference(CompactControlReadability, false)

Revolver.settings
