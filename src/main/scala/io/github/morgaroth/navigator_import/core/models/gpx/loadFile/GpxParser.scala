package io.github.morgaroth.navigator_import.core.models.gpx.loadFile

import com.droelf.gpxparser.gpxtype.{GPX, GPXDecoder}

object GpxParser {
  def loadGPX(s: String): GPX = GPXDecoder.decodeFromString(s)
}
