package io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile

import io.github.morgaroth.navigator_import.core.models.XMLParsingUtils
import io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile.Route._

import scala.util.Try
import scala.xml.{Node, Elem, XML}

case class RoutingPoints(
                          default: Option[Route],
                          rest: List[Route]
                          )

object RoutingPoints extends XMLParsingUtils {
  def readFromXMLImpl(elem: Node): Option[RoutingPoints] = {
    val default = readSingleValue(elem, "default_set").map(Route.readFromXML).flatten
    val rest = readList(elem, "set").map(_.map(Route.readFromXML))
    val allProper = rest.map(_.forall(_.isDefined)).getOrElse(true)
    if (!allProper) {
      // TODO WARN
    }
    Some(RoutingPoints(default, rest.map(_.flatten).getOrElse(List.empty[Route])))
  }

  def readFromXML(xml: String) = asEither(readFromXMLImpl(XML.loadString(xml)))

  def asEither[Value](f: => Option[Value]): Either[Throwable, Value] =
    try {
      f match {
        case Some(value) => Right(value)
        case _ => Left(new IllegalArgumentException("value is none"))
      }
    }
    catch {
      case t: Throwable => Left(t)
    }
}
