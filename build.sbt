import sbtrelease.ReleaseStateTransformations._
import sbtrelease.ReleaseStep
import SbtReleaseHelpers._

organization := "io.github.morgaroth"

name := "navigator-import-core"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2" withSources(),
  "org.specs2" %% "specs2-core" % "2.4.15" % "test" withSources(),
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3" withSources()
)

buildInfoSettings

buildInfoKeys := Seq[BuildInfoKey](
  name, version, scalaVersion, sbtVersion, libraryDependencies, resolvers
)

buildInfoPackage := "io.github.morgaroth.navigator_import.core.build"

sourceGenerators in Compile <+= buildInfo

releaseSettings

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies, // : ReleaseStep
  inquireVersions, // : ReleaseStep
  runTest, // : ReleaseStep
  setReleaseVersion, // : ReleaseStep
  publishArtifactsSigned,
  finishReleaseAtSonatype,
  commitReleaseVersion, // : ReleaseStep, performs the initial git checks
  tagRelease, // : ReleaseStep
  setNextVersion, // : ReleaseStep
  commitNextVersion, // : ReleaseStep
  pushChanges // : ReleaseStep, also checks that an upstream branch is properly configured
)

pomExtra := SbtSonatypeHelpers.githubPom("navigator-import-core")

publishTo :=  SbtSonatypeHelpers.publishToGen(version.value)

publishArtifact in Test := false

// Do not include log4jdbc as a dependency.
pomPostProcess := PackagingHelpers.removeTestOrSourceDependencies