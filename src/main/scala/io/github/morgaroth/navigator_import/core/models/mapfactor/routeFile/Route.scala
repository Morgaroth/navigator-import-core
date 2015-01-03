package io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile

import io.github.morgaroth.navigator_import.core.models.XMLParsingUtils

import scala.xml.{Node, Elem, XML}

case class Route(
                  name: Option[String],
                  departure: Option[Waypoint],
                  waypoints: List[Waypoint],
                  destination: Option[Waypoint]
                  )

object Route extends XMLParsingUtils {
  def readFromXMLImpl(elem: Node): Option[Route] = {
    val name = readSingleValue(elem, "name").map(_.text)
    val dep = readSingleValue(elem, "departure").map(Waypoint.readFromXML).flatten
    val waypts = readList(elem, "waypoint").map(_.map(Waypoint.readFromXML))
    val dest = readSingleValue(elem, "destination").map(Waypoint.readFromXML).flatten

    val allProper = waypts.map(_.forall(_.isDefined)).getOrElse(true)
    if (!allProper) {
      // TODO ERROR
    }
    val flattenWaypts: List[Waypoint] = waypts.map(_.flatten).getOrElse(List.empty[Waypoint])
    (name, dep, flattenWaypts, dest) match {
      case (_, None, waypoints, None) if waypoints.isEmpty => None
      case _ => Some(Route(name, dep, flattenWaypts, dest))
    }
  }

  def readFromXML(xml: String): Option[Route] = readFromXMLImpl(XML.loadString(xml))

  def readFromXML(xml: Elem): Option[Route] = readFromXMLImpl(xml)

  def readFromXML(xml: Node): Option[Route] = readFromXMLImpl(xml)
}