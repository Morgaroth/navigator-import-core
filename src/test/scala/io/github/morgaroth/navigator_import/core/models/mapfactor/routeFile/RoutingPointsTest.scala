package io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile

import org.specs2.mutable.Specification

class RoutingPointsTest extends Specification {

  "parsing entire routing_points.xml" should {
    "parse correctly empty default" in {
      val objOpt = RoutingPoints.readFromXML(emptyDefault)
      objOpt must beSome
      val obj = objOpt.get
      obj.default must beNone
    }
    "parse correctly element without begin point" in {
      val objOpt = RoutingPoints.readFromXML(onewithoutBegin)
      objOpt must beSome
      val obj = objOpt.get
      obj.rest must have size 1
      obj.rest(0).departure must beNone
      obj.rest(0).waypoints must have size 0
      obj.rest(0).destination must beSome
    }
    "parse correctly element without end point" in {
      val objOpt = RoutingPoints.readFromXML(onewithoutEnd)
      objOpt must beSome
      val obj = objOpt.get
      obj.rest must have size 1
      obj.rest(0).departure must beSome
      obj.rest(0).waypoints must have size 0
      obj.rest(0).destination must beNone
    }
    "parse correctly element without both ends" in {
      val objOpt = RoutingPoints.readFromXML(oneWithoutEnds)
      objOpt must beSome
      val obj = objOpt.get
      obj.rest must have size 1
      obj.rest(0).departure must beNone
      obj.rest(0).waypoints must have size 2
      obj.rest(0).destination must beNone
    }
    "parse correctly full file" in {
      val objOpt = RoutingPoints.readFromXML(full)
      objOpt must beSome
      val obj = objOpt.get
      obj.default must beSome
      obj.rest must have size 5
    }
  }

  val emptyDefault = """<?xml version="1.0" encoding="utf-8"?>
    <routing_points>
      <default_set/>
    </routing_points>"""

  val onewithoutBegin = """<?xml version="1.0" encoding="utf-8"?>
    <routing_points>
      <default_set/>
      <set>
        <name>bez poczatku</name>
        <destination>
          <name>Kolejowa, powiat tarnowski, Poland</name> <lat>179614544</lat> <lon>75796071</lon>
        </destination>
      </set>
    </routing_points>"""

  val onewithoutEnd = """<?xml version="1.0" encoding="utf-8"?>
    <routing_points>
      <default_set/>
      <set>
        <name>bez konca</name>
        <departure>
          <name>Ignacego Daszyńskiego, Adama Mickiewicza, Rynek</name> <lat>179620072</lat> <lon>75794589</lon>
        </departure>
      </set>
    </routing_points>"""

  val oneWithoutEnds = """<?xml version="1.0" encoding="utf-8"?>
    <routing_points>
      <default-set/>
      <set>
        <name>wewnatrz tylko</name>
        <waypoint>
          <name>Juliusza Lea, Antoniego Gramatyka, Warmijska</name> <lat>180258646</lat> <lon>71663371</lon>
        </waypoint>
        <waypoint>
          <name>Walerego Goetla, Piastowska, Osiedle Cichy Kącik</name> <lat>180240687</lat> <lon>71651645</lon>
        </waypoint>
      </set>
    </routing_points>"""

  val full = """<?xml version="1.0" encoding="utf-8"?>
    <routing_points>
      <set>
        <name>dom lidzia lasek krakow</name>
        <departure>
          <name>powiat tarnowski, województwo małopolskie, Poland</name>
          <lat>179125628</lat>
          <lon>75299406</lon>
        </departure>
        <waypoint>
          <name>Węgierska, Biała, powiat gorlicki</name>
          <lat>178936282</lat>
          <lon>75372856</lon>
        </waypoint>
        <waypoint>
          <name>powiat nowosądecki, województwo małopolskie, Poland</name>
          <lat>178990286</lat>
          <lon>74978592</lon>
        </waypoint>
        <waypoint>
          <name>Jezioro Rożnowskie, powiat nowosądecki, województwo małopolskie</name>
          <lat>179117501</lat>
          <lon>74608248</lon>
        </waypoint>
        <waypoint>
          <name>K1555, K1612, powiat limanowski</name>
          <lat>179134187</lat>
          <lon>73622042</lon>
        </waypoint>
        <destination>
          <name>Juliana Tokarskiego 6, Kraków, Kraków, Polska</name>
          <lat>180248232</lat>
          <lon>71647360</lon>
        </destination>
      </set>
      <set>
        <name>nad morze test</name>
        <departure>
          <name>49.7571118,20.9164726</name>
          <lat>179125560</lat>
          <lon>75299328</lon>
        </departure>
        <waypoint>
          <name>49.407181,22.4561107</name>
          <lat>177865848</lat>
          <lon>80841996</lon>
        </waypoint>
        <waypoint>
          <name>Sandomierz</name>
          <lat>182455956</lat>
          <lon>78300756</lon>
        </waypoint>
        <waypoint>
          <name>53.7390914,21.6143595</name>
          <lat>193460256</lat>
          <lon>77787900</lon>
        </waypoint>
        <waypoint>
          <name>Malbork</name>
          <lat>194530068</lat>
          <lon>68536692</lon>
        </waypoint>
        <waypoint>
          <name>54.5195532,16.5275259</name>
          <lat>196270380</lat>
          <lon>59499072</lon>
        </waypoint>
        <destination>
          <name>53.941546,14.4806706</name>
          <lat>194189544</lat>
          <lon>52130412</lon>
        </destination>
      </set>
      <default_set>
        <departure>
          <name>Ignacego Daszyńskiego, Adama Mickiewicza, Rynek</name>
          <lat>179620072</lat>
          <lon>75794589</lon>
        </departure>
        <destination>
          <name>Euronet, Plac Dworcowy, ID=584461233</name>
          <lat>180021282</lat>
          <lon>75508316</lon>
        </destination>
      </default_set>
      <set>
        <name>wewnatrz tylko</name>
        <waypoint>
          <name>Juliusza Lea, Antoniego Gramatyka, Warmijska</name> <lat>180258646</lat> <lon>71663371</lon>
        </waypoint>
        <waypoint>
          <name>Walerego Goetla, Piastowska, Osiedle Cichy Kącik</name> <lat>180240687</lat> <lon>71651645</lon>
        </waypoint>
      </set>
      <set>
        <name>bez poczatku</name>
        <destination>
          <name>Kolejowa, powiat tarnowski, Poland</name> <lat>179614544</lat> <lon>75796071</lon>
        </destination>
      </set>
      <set>
        <name>bez konca</name>
        <departure>
          <name>Ignacego Daszyńskiego, Adama Mickiewicza, Rynek</name> <lat>179620072</lat> <lon>75794589</lon>
        </departure>
      </set>
    </routing_points>"""
}
