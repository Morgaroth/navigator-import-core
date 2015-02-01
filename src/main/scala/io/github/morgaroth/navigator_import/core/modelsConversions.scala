package io.github.morgaroth.navigator_import.core

import com.droelf.gpxparser.gpxtype.{GPXWayPoint, GPX}
import io.github.morgaroth.navigator_import.core.global.{Waypoint, Route}
import io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile.{Waypoint => NaviWaypoint, Route => NaviRoute}

import scala.language.{reflectiveCalls, implicitConversions}

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

    implicit def `convert gpx file by method`(in: GPX): Object {def toRoute: Route} = new {
      def toRoute: Route = `convert gpx file to main route object`(in)
    }
  }

  object fromGpx extends fromGpx

  trait fromGoogleUrl{

  }

}
