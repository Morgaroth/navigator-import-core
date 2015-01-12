package io.github.morgaroth.navigator_import.core

import com.droelf.gpxparser.gpxtype.GPXDecoder
import io.github.morgaroth.navigator_import.core.build.BuildInfo
import io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile.RoutingPoints

import scala.xml.{Node, NodeSeq}

object Core {
  def loadRoutingPoints(data: String) = RoutingPoints.readFromXML(data)

  def loadGpx(data: String) = GPXDecoder.decodeFromString(data)

  def buildInfo = BuildInfo

  def toNodeSeq(rp: RoutingPoints): NodeSeq = {NodeSeq.fromSeq(Seq(
    rp.rest.map(r => Some(<set>{r.toXML}</set>)),
    List(rp.default.map(x => <default_set>{x.toXML}</default_set>).orElse(Some(<default_set/>)))
  ).flatten.flatten)}

  def toXML(rp: RoutingPoints): Node = {
    scala.xml.Utility.trim(<routing_points>
      {toNodeSeq(rp)}
    </routing_points>)
  }
}
