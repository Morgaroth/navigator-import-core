package io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile

import org.specs2.mutable.Specification

class RouteTest extends Specification {

  "readFromXML" should {
    "return correct object" in {
      val objOpt = Route.readFromXML(routeXML)
      objOpt must beSome
      val obj: Route = objOpt.get
      obj.name must beEqualTo(name)

      obj.departure.get.name must beEqualTo(depName)
      obj.departure.get.lat must beEqualTo(depLat)
      obj.departure.get.lon must beEqualTo(depLon)

      obj.waypoints must have size 2
      obj.waypoints(0).name must beEqualTo(wpt1Name)
      obj.waypoints(0).lat must beEqualTo(wpt1Lat)
      obj.waypoints(0).lon must beEqualTo(wpt1Lon)

      obj.waypoints(1).name must beEqualTo(wpt2Name)
      obj.waypoints(1).lat must beEqualTo(wpt2Lat)
      obj.waypoints(1).lon must beEqualTo(wpt2Lon)

      obj.destination.get.name must beEqualTo(destName)
      obj.destination.get.lat must beEqualTo(destLat)
      obj.destination.get.lon must beEqualTo(destLon)
    }
  }

  private val name: String = "nad morze test"
  private val depName: String = "49.7571118,20.9164726"
  private val depLat = 179125560L
  private val depLon = 75299328L
  private val wpt1Name: String = "49.407181,22.4561107"
  private val wpt1Lat = 177865848L
  private val wpt1Lon = 80841996L
  private val wpt2Name: String = "Sandomierz"
  private val wpt2Lat = 182455956L
  private val wpt2Lon = 78300756L
  private val destName: String = "53.941546,14.4806706"
  private val destLat = 194189544L
  private val destLon = 52130412L

  val routeXML = s"""<set>
        <name>$name</name>
        <departure>
            <name>$depName</name>
            <lat>$depLat</lat>
            <lon>$depLon</lon>
        </departure>
        <waypoint>
            <name>$wpt1Name</name>
            <lat>$wpt1Lat</lat>
            <lon>$wpt1Lon</lon>
        </waypoint>
        <waypoint>
            <name>$wpt2Name</name>
            <lat>$wpt2Lat</lat>
            <lon>$wpt2Lon</lon>
        </waypoint>
        <destination>
            <name>$destName</name>
            <lat>$destLat</lat>
            <lon>$destLon</lon>
        </destination>
    </set>"""
}
