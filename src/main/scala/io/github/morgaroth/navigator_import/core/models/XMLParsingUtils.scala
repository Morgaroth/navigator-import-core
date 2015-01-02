package io.github.morgaroth.navigator_import.core.models

import scala.xml.Node

trait XMLParsingUtils {
  def readSingleValue(elem: Node, name: String): Option[Node] = {
    (elem \ name).toList match {
      case value :: Nil => Some(value)
      case Nil => None
      case listOfValues: List[Node] =>
        // TODO WARNING
        Some(listOfValues.head)
    }
  }
  def readList(elem: Node, name: String): Option[List[Node]] = {
    (elem \ name).toList match {
      case Nil => None
      case listOfValues: List[Node] => Some(listOfValues)
    }
  }
}