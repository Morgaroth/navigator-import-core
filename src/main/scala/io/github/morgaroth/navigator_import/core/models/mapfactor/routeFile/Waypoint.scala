package io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile

import io.github.morgaroth.navigator_import.core.models.XMLParsingUtils

import scala.xml.{Node, Elem, XML}


case class Waypoint(
                     name: String,
                     lat: Long,
                     lon: Long
                     )

object Waypoint extends XMLParsingUtils {
  def readFromXMLImpl(elem: Node): Option[Waypoint] = {
    val nameOpt = readSingleValue(elem, "name").map(_.text)
    val latOpt = readSingleValue(elem, "lat").map(_.text.toLong)
    val lonOpt = readSingleValue(elem, "lon").map(_.text.toLong)
    (nameOpt, latOpt, lonOpt) match {
      case (Some(name), Some(lat), Some(lon)) => Some(Waypoint(name, lat, lon))
      case _ => None
    }
  }

  def readFromXML(xml: String): Option[Waypoint] = readFromXMLImpl(XML.loadString(xml))
  def readFromXML(xml: Elem): Option[Waypoint] = readFromXMLImpl(xml)
  def readFromXML(xml: Node): Option[Waypoint] = readFromXMLImpl(xml)
}

