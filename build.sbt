import com.typesafe.sbt.pgp.PgpKeys._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.ReleaseStep
import sbtrelease.Utilities._

organization := "io.github.morgaroth"

name := "navigator-import-core"

scalaVersion := "2.11.5"

resolvers += "JavaApiForKML" at "http://download.java.net/maven/2"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2" withSources(),
  "org.specs2" %% "specs2-core" % "2.4.15" % "test" withSources(),
  "de.micromata.jak" % "JavaAPIforKml" % "2.2.0-SNAPSHOT"
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

val publishArtifactsSigned = ReleaseStep(action = (st: State) => {
  val extracted = st.extract
  val ref = extracted.get(thisProjectRef)
  extracted.runAggregated(publishSigned in Global in ref, st)
})

sonatypeSettings

import SonatypeKeys._

val finishReleseAtSonatype = ReleaseStep(action = (st: State) => {
  val extracted = st.extract
  val ref = extracted.get(thisProjectRef)
  extracted.runAggregated(sonatypeReleaseAll in Global in ref, st)
})

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies, // : ReleaseStep
  inquireVersions, // : ReleaseStep
  runTest, // : ReleaseStep
  setReleaseVersion, // : ReleaseStep
  publishArtifactsSigned,
  finishReleseAtSonatype,
  commitReleaseVersion, // : ReleaseStep, performs the initial git checks
  tagRelease, // : ReleaseStep
  setNextVersion, // : ReleaseStep
  commitNextVersion, // : ReleaseStep
  pushChanges // : ReleaseStep, also checks that an upstream branch is properly configured
)

pomExtra := <url>https://bitbucket.org/Morgaroth/navigator-import-core/overview</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@bitbucket.org:Morgaroth/navigator-import-core.git</url>
    <connection>scm:git:git@bitbucket.org:Morgaroth/navigator-import-core.git</connection>
  </scm>
  <developers>
    <developer>
      <id>morgaroth</id>
      <name>Mateusz Jaje</name>
    </developer>
  </developers>

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

// Do not include log4jdbc as a dependency.
pomPostProcess := { (node: scala.xml.Node) =>
  val rewriteRule =
    new scala.xml.transform.RewriteRule {
      override def transform(n: scala.xml.Node): scala.xml.NodeSeq = {
        def testIfRemove(dep: scala.xml.Node) =
          ((dep \ "scope").text == "test") ||
            ((dep \ "classifier").text == "sources")
        val name = n.nameToString(new StringBuilder).toString()
        if (name == "dependency") {
          if (testIfRemove(n)) scala.xml.NodeSeq.Empty
          else n
        }
        else {
          n
        }
      }
    }
  val transformer = new scala.xml.transform.RuleTransformer(rewriteRule)
  transformer.transform(node)(0)
}