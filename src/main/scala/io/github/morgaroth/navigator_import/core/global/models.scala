package io.github.morgaroth.navigator_import.core.global

case class Waypoint(
                     name: Option[String],
                     latitude: Double,
                     longitude: Double
                     )

case class Route(
                  name: Option[String],
                  departure: Option[Waypoint],
                  waypoints: List[Waypoint],
                  destination: Option[Waypoint]
                  )
