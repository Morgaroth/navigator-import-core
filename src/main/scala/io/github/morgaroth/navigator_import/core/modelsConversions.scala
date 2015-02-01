package io.github.morgaroth.navigator_import.core

import com.droelf.gpxparser.gpxtype.{GPX, GPXWayPoint}
import io.github.morgaroth.navigator_import.core.global.{Route, Waypoint}
import io.github.morgaroth.navigator_import.core.models.googleurl.parser.{URL, Wpt}
import io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile.{Route => NaviRoute, Waypoint => NaviWaypoint}

import scala.language.{implicitConversions, reflectiveCalls}

object modelsConversions {

  implicit def convertvalueToSome[T](value: T): Object {def asOption: Option[T]} = new {
    def asOption: Option[T] = Option(value)
  }

  trait itudesConverter {
    def itudeToLong(itude: Double): Long = (itude * 3600000).toLong

    def itudeToDouble(itude: Long): Double = itude * 1.0 / 3600000

    implicit def convertItudefromDegressToNavigatorsLongNotation(d: Double): Long = itudeToLong(d)

    implicit def convertItudefromNavigatorsLongNotationToDegress(d: Long): Double = itudeToDouble(d)
  }

  object itudesConverter extends itudesConverter

  trait toNavigator extends itudesConverter {

    implicit def `convert Waypoint To NavigatorWaypoint`(in: Waypoint): NaviWaypoint = NaviWaypoint(in.name.getOrElse("unnamed"), lat = in.latitude, lon = in.longitude)

    implicit def convertByMethod(in: Waypoint): Object {def toNavigatorWaypoint: NaviWaypoint} = new {
      def toNavigatorWaypoint: NaviWaypoint = `convert Waypoint To NavigatorWaypoint`(in)

      def toNavigatorDeparture: NaviWaypoint = `convert Waypoint To NavigatorWaypoint`(in)

      def toNavigatorDestination: NaviWaypoint = `convert Waypoint To NavigatorWaypoint`(in)

    }
  }

  object toNavigator extends toNavigator


  trait fromGpx {
    implicit def `convert gpx waypoint to main waypoint`(in: GPXWayPoint): Waypoint =
      Waypoint(in.name, latitude = in.latitude, longitude = in.longitude)

    implicit def `convert gpx waypoint by method`(in: GPXWayPoint): Object {def toWaypoint: Waypoint} = new {
      def toWaypoint: Waypoint = `convert gpx waypoint to main waypoint`(in)
    }

    implicit def `convert gpx file to main route object`(in: GPX): Route = {
      val departure: Some[Waypoint] = Some(in.waypoints.head.toWaypoint)
      val destination: Some[Waypoint] = Some(in.waypoints.last.toWaypoint)
      val wpts: List[Waypoint] = in.waypoints.tail.init.map(_.toWaypoint)
      Route(None, departure, wpts, destination)
    }

    implicit def `convert gpx file to main route object by method`(in: GPX): Object {def toRoute: Route} = new {
      def toRoute: Route = `convert gpx file to main route object`(in)
    }
  }

  object fromGpx extends fromGpx

  trait fromGoogleUrl {
    implicit def `convert google waypoint to main waypoint`(in: Wpt): Waypoint =
      Waypoint(Some(s"${in.longitude},${in.latitude}"), latitude = in.latitude, longitude = in.longitude)

    implicit def `convert google waypoint by method`(in: Wpt): Object {def toWaypoint: Waypoint} = new {
      def toWaypoint: Waypoint = `convert google waypoint to main waypoint`(in)
    }

    implicit def `convert google URL to main route object`(inUrl: URL): Route = {
      val in = inUrl.toListOfWaypoints.right.get
      val departure: Some[Waypoint] = Some(in.head.toWaypoint)
      val destination: Some[Waypoint] = Some(in.last.toWaypoint)
      val wpts: List[Waypoint] = in.tail.init.map(_.toWaypoint)
      Route(None, departure, wpts, destination)
    }

    implicit def `convert google URL to main route object by method`(in: URL): Object {def toRoute: Route} = new {
      def toRoute: Route = `convert google URL to main route object`(in)
    }
  }

  object fromGoogleUrl extends fromGoogleUrl

}
