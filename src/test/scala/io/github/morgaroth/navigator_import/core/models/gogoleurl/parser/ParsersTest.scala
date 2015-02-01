package io.github.morgaroth.navigator_import.core.models.gogoleurl.parser

import org.specs2.mutable.Specification

import scala.util.parsing.input.CharSequenceReader

class ParsersTest extends Specification {
  "Waypoint parser" should {
    "parse word waypoint" in {
      val tested = "Zarzecze+66,+33-332+Krak%C3%B3w/"
      val result = main.wpt(new CharSequenceReader(tested))
      val succ = result.asInstanceOf[main.Success[WordsWpt]]
      succ.result.body mustEqual "Zarzecze+66,+33-332+Krak%C3%B3w"
      succ.next.atEnd must beFalse
    }
    "parse normal waypoint" in {
      val tested = "50.0712632,19.8950682"
      main.phrase(main.wpt)(new CharSequenceReader(tested)) match {
        case main.Success(value: NormalWpt, _) =>
          value.longitude must beEqualTo(50.0712632)
          value.latitude must beEqualTo(19.8950682)
        case t => failure("received " + t)
      }
      success
    }
  }

  "itude parser" should {
    "parse itude" in {
      val tested = "50.0712632"
      main.phrase(main.itudeParser)(new CharSequenceReader(tested)) match {
        case main.Success(value, _) =>
          value must beEqualTo(50.0712632)
        case t => failure("received " + t)
      }
      success
    }
  }
  "zoom parser" should {
    implicit val test = main.zoom
    "parse zoom" in {
      parse("14z") match {
        case main.Success(v, _) =>
          v must beEqualTo(14)
        case t => failure("received " + t)
      }
      success
    }
  }

  "waypoints parser" should {
    implicit val test = main.wpts
    "parse link with both start and target" in {
      parse("50.0712632,19.8950682/50.0646796,19.9183283") match {
        case main.Success(value, _) =>
        case t => failure("received " + t)
      }
      success
    }
    "parse link with only start" in {
      parse("50.0712632,19.8950682/") match {
        case main.Success(value, _) =>
          value must haveSize(2)
          value.head.asInstanceOf[NormalWpt]
          value.tail.head must beEqualTo(NoWpt)
        case t => failure("received " + t)
      }
      success
    }
    "parse link with only target" in {
      parse("/50.0646796,19.9183283") match {
        case main.Success(value, _) =>
          value must haveSize(2)
          value.head must beEqualTo(NoWpt)
          value.tail.head.asInstanceOf[NormalWpt]
        case t => failure("received " + t)
      }
      success
    }
    "left /@ during parsing waypoints" in {
      import main.{failure => _, success => _, parse => _}
      parse("50.0712632,19.8950682/50.0712632,19.8950682/@")(main.wpts ~ "/@" ^^ (x => x._1 -> x._2)) match {
        case main.Success(value, rest) =>
          value._1 must haveSize(2)
          value._2 must_== "/@"
          value._1.head.asInstanceOf[NormalWpt]
          value._1.tail.head.asInstanceOf[NormalWpt]
        case t => failure("received " + t)
      }
      success
    }
  }
  "first part parser" should {
    implicit val test = main.firstPart
    "parse link with both target and departure" in {
      parse("50.0712632,19.8950682//@50.0678889,19.9024068,15z") match {
        case main.Success(value, _) =>
          value._1 must haveSize(2)
        case t => failure("received " + t)
      }
      success
    }
  }

  "entire link parser" should {
    implicit val testedParser = main.entireUrl
    "parse empty link with no track" in {
      val tested = "https://www.google.pl/maps/@50.0678889,19.9024068,15z"
      parse(tested) match {
        case main.Success(value: OnlyView, _) =>
        case t => failure("received " + t)
      }
      success
    }
    "parse link with only start point" in {
      val tested = "https://www.google.pl/maps/dir/50.0712632,19.8950682//@50.0678889,19.9024068,15z"
      parse(tested) match {
        case main.Success(value: URL, _) =>
        case t => failure("received " + t)
      }
      success
    }
  }

  def parse[T](toParse: String)
              (implicit p: main.Parser[T]): main.ParseResult[T] =
    main.phrase(p)(new CharSequenceReader(toParse))

  def parseNotAll[T](toParse: String)
                    (implicit p: main.Parser[T]): main.ParseResult[T] =
    p(new CharSequenceReader(toParse))

}
