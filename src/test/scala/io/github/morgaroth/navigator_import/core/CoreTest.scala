package io.github.morgaroth.navigator_import.core

import io.github.morgaroth.navigator_import.core.models.mapfactor.routeFile.RoutingPoints
import org.specs2.mutable.Specification

import scala.xml.{Node, Utility}

class CoreTest extends Specification {
  
  "Core" should {
    "correctly serialize empty routes" in {
      val data = RoutingPoints(None,List.empty)
      val result = Core.toXML(data)
      val trimmed = result.mkString
      trimmed must beEqualTo("<routing_points><default_set/></routing_points>")
    }
    
  }

}
