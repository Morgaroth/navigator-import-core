package io.github.morgaroth.navigator_import.core

import com.droelf.gpxparser.gpxtype.GPXDecoder
import io.github.morgaroth.navigator_import.core.global.Route
import io.github.morgaroth.navigator_import.core.models.googleurl.parser.main
import io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile.RoutingPoints

import scala.language.reflectiveCalls

object Loaders extends modelsConversions.fromGpx with modelsConversions.fromGoogleUrl {

  def loadRoutingPoints(data: String): Either[Throwable, RoutingPoints] = RoutingPoints.readFromXML(data)

  def loadGpx(data: String): Either[Throwable, Route] = GPXDecoder.decodeFromString(data).right.map(_.toRoute)

  def loadGoogleUrl(link: String): Either[String, Route] = main.parseLink(link).right.map(_.toRoute)

}
