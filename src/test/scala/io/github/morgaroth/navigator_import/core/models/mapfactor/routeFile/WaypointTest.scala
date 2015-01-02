package io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile

class WaypointTest extends org.specs2.mutable.Specification {

  private val wptName: String = "Węgierska, Biała, powiat gorlicki"
  private val wptLat = 178936282L
  private val wptLon = 75372856L
  val waypointXML = s"""<waypoint>
    <name>$wptName</name>
    <lat>$wptLat</lat>
    <lon>$wptLon</lon>
  </waypoint>"""

  "readFromXML" should {
    "return correct object" in {
      val objOpt = Waypoint.readFromXML(waypointXML)
      objOpt must beSome
      val obj: Waypoint = objOpt.get
      obj.name must beEqualTo(wptName)
      obj.lat must beEqualTo(wptLat)
      obj.lon must beEqualTo(wptLon)
    }
  }
}
