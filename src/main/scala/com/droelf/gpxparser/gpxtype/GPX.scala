package com.droelf.gpxparser.gpxtype

import scala.xml.Node

class GPX(var version: String,
          var creator: String,
          var metadata: Option[GPXMetadata] = None,
          var waypoints: List[GPXWayPoint] = List.empty,
          var routes: List[GPXRoute] = List.empty,
          var tracks: List[GPXTrack] = List.empty,
          var extensions: Option[Node] = None) {

  override def toString() =
    "Version: " + version +
    "\n\nCreator: " + creator +
    "\n\nMetadata: " + metadata +
    "\n\nWayPoints: " + waypoints.foldLeft("")(_ + " " + _)+
    "\n\nRoutes: " + routes.foldLeft("")(_ + " " + _) +
    "\n\nTracks: " + tracks.foldLeft("")(_ + " " + _)
}
