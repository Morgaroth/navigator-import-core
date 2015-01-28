package io.github.morgaroth.navigator_import.core.models.gpx.loadFile

import org.specs2.mutable.Specification

import scala.io.{BufferedSource, Source}

class GpxParserTest extends Specification {

  "a GPX loader" should {
    "load gpx string correctly" in {
      val file: BufferedSource = Source.fromURL(getClass.getResource("/test.gpx"))
      val string: String = file.mkString
      val GPX = GpxParser.loadGPX(string)
      GPX must beRight
      GPX.right.get.departure must beSome
      GPX.right.get.destination must beSome
      GPX.right.get.waypoints must have size 1
    }
  }
}
