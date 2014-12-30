import sbtrelease.ReleaseStep
import sbtrelease._
import ReleaseStateTransformations._

name := "navigator-import-core"

version := "1.0"

scalaVersion := "2.11.4"

//sourceGenerators in Compile += Def.task {
//  val file = (sourceManaged in Compile).value / "generated" /  "Generated.scala"
//  println(file.getAbsolutePath)
//  IO.write(file, "package generated; object Generated { val version = \"1.2.0\" }")
//  Seq(file)
//}.taskValue

buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, buildInfoBuildNumber)

buildInfoPackage := "generated.info"

releaseSettings

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)
