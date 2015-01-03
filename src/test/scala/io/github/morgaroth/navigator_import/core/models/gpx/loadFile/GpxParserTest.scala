package io.github.morgaroth.navigator_import.core.models.gpx.loadFile

import org.specs2.mutable.Specification

import scala.io.{BufferedSource, Source}

class GpxParserTest extends Specification {

  "a GPX loader" should {
    "load gpx string correctly" in {
      val file: BufferedSource = Source.fromURL(getClass.getResource("/test.gpx"))
      val string: String = file.mkString
      val GPX = GpxParser.loadGPX(string)
      GPX must not(beNull)
      GPX.waypoints must have size 3
    }
  }
}