import sbtrelease.ReleaseStateTransformations._
import sbtrelease.ReleaseStep
import sbtrelease.Utilities._

organization  := "io.github.morgaroth"

name := "navigator-import-core"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2" withSources(),
  "org.specs2" %% "specs2-core" % "2.4.15" % "test" withSources()
)

buildInfoSettings

buildInfoKeys := Seq[BuildInfoKey](
  name, version, scalaVersion, sbtVersion, libraryDependencies, resolvers
)

buildInfoPackage := "io.github.morgaroth.navigator_import.core.build"

sourceGenerators in Compile <+= buildInfo

releaseSettings

val publishArtifactsLocally = ReleaseStep(action = (st: State) => {
  val extracted = st.extract
  val ref = extracted.get(thisProjectRef)
  extracted.runAggregated(publishLocal in Global in ref, st)
})

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  publishArtifactsLocally,
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)
