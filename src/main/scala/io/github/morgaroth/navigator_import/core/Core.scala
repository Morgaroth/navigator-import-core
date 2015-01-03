package io.github.morgaroth.navigator_import.core

import com.droelf.gpxparser.gpxtype.GPXDecoder
import io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile.RoutingPoints

object Core {
  def loadRoutingPoints(data: String) = RoutingPoints.readFromXML(data)

  def loadGpx(data: String) = GPXDecoder.decodeFromString(data)
}
