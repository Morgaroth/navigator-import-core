package io.github.morgaroth.navigator_import.core

import com.droelf.gpxparser.gpxtype.GPXDecoder
import io.github.morgaroth.navigator_import.core.build.BuildInfo
import io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile.RoutingPoints

object Core {
  def loadRoutingPoints(data: String) = RoutingPoints.readFromXML(data)

  def loadGpx(data: String) = GPXDecoder.decodeFromString(data)

  def buildInfo = BuildInfo

  def toXML(rp: RoutingPoints) =
    <routing_points>
      {
        List(
          List(rp.default.map(x => <default_set>{x.toXML}</default_set>)),
          rp.rest.map(r => Some(<set>{r.toXML}</set>))
        ).flatten.flatten
      }
    </routing_points>
}
