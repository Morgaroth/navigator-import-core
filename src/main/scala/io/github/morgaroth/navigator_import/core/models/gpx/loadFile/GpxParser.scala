package io.github.morgaroth.navigator_import.core.models.gpx.loadFile

import com.droelf.gpxparser.gpxtype.GPXDecoder
import io.github.morgaroth.navigator_import.core.global.Route
import io.github.morgaroth.navigator_import.core.modelsConversions

import scala.language.reflectiveCalls

object GpxParser extends modelsConversions.fromGpx {
  def loadGPX(s: String): Either[Throwable, Route] = {
    val rawGpx = GPXDecoder.decodeFromString(s)
    rawGpx.right.map(_.toRoute)
  }
}
