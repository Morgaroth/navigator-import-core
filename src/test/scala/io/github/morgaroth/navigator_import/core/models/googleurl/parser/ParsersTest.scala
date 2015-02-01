package io.github.morgaroth.navigator_import.core.models.googleurl.parser

import org.specs2.execute.Success
import org.specs2.mutable.Specification

import scala.util.parsing.input.{Reader, CharSequenceReader}

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
        case main.Success(value: Wpt, _) =>
          value.longitude must beEqualTo(19.8950682)
          value.latitude must beEqualTo(50.0712632)
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
          value.head.asInstanceOf[Wpt]
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
          value.tail.head.asInstanceOf[Wpt]
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
          value._1.head.asInstanceOf[Wpt]
          value._1.tail.head.asInstanceOf[Wpt]
        case t => failure("received " + t)
      }
      success
    }
  }
  "first part parser" should {
    implicit val test: main.Parser[(List[BaseWpt], Wpt, Int)] = main.firstPart
    "parse link with both target and departure" in {
      parse("50.0712632,19.8950682//@50.0678889,19.9024068,15z") match {
        case main.Success(value, _) =>
          value._1 must haveSize(2)
        case t => failure("received " + t)
      }
      success
    }
    "left /data= as not parsed already" in {
      testPattern(links.twoWpt_oneBetween_firstAndSecond)(test ~> "/data=.*".r).apply {
        case v: String =>
          v must startWith("/data=")
      }
    }
  }

  "anononomous block parser" should {
    implicit val testedParser = main.anonBlock
    "parse block with one waypoint inside" in {
      testPattern("!1m5!3m4!1m2!1d19.9036674!2d50.0767216!3s0x47165bb65b7bf787:0x518bd8fa107baf09").apply {
        case wpt =>
          wpt must haveSize(1)
      }
    }
  }

  "entire link parser" should {
    implicit val testedParser: main.Parser[Result] = main.entireUrl
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
          value.waypoints must haveSize(2)
          value.waypoints(0) must_== Wpt(19.8950682, 50.0712632)
          value.waypoints(1) must_== NoWpt
        case t => failure("received " + t)
      }
      success
    }
    "parse link with only target point" in {
      val tested = "https://www.google.pl/maps/dir//50.0712632,19.8950682/@50.0678889,19.9024068,15z"
      testPattern(tested).apply {
        case value: URL =>
          value.waypoints must haveSize(2)
          value.waypoints(0) must_== NoWpt
          value.waypoints(1) must_== Wpt(19.8950682, 50.0712632)
      }
    }
    "parse link with both departure and destination points" in {
      val tested = "https://www.google.pl/maps/dir/50.0678889,19.9024068/50.0712632,19.8950682/@50.0678889,19.9024068,15z"
      testPattern(tested).apply {
        case value: URL =>
          value.waypoints must haveSize(2)
          value.waypoints(0) must_== Wpt(19.9024068, 50.0678889)
          value.waypoints(1) must_== Wpt(19.8950682, 50.0712632)
      }
    }
    "parse link with two waypoints and one nonymous between them" in {
      testPattern(links.twoWpt_oneBetween_firstAndSecond).apply {
        case value: URL =>
          value.waypoints must haveSize(2)
          value.anonymousWpts must haveSize(2)
          value.anonymousWpts(0) must haveSize(1)
          value.anonymousWpts(0)(0) must_== Wpt(19.9036674, 50.0767216)
          value.anonymousWpts(1) must beEmpty
          value.waypoints(0) must_== Wpt(19.9024068, 50.0678889)
          value.waypoints(1) must_== Wpt(19.8950682, 50.0712632)
      }
    }
    "parse link with two waypoints and nothing between" in {
      testPattern(links.twoWpt_nothingBetween).apply {
        case value: URL =>
          value.anonymousWpts must haveSize(0)
      }
    }
  }

  def parse[T](toParse: String)
              (implicit p: main.Parser[T]): main.ParseResult[T] =
    main.phrase(p)(new CharSequenceReader(toParse))

  def parseNotAll[T](toParse: String)
                    (implicit p: main.Parser[T]): main.ParseResult[T] =
    p(new CharSequenceReader(toParse))

  def testPattern[T](toTest: String)(implicit p: main.Parser[T]) = {
    (ifSucc: PartialFunction[T, Any]) => {
      main.phrase(p)(new CharSequenceReader(toTest)) match {
        case main.Success(value, _) if ifSucc.isDefinedAt(value) =>
          ifSucc(value)
        case t => failure(s"not parsed, received: $t")
      }
      success
    }
  }

  def testPattern2[T](toTest: String)(implicit p: main.Parser[T]) = {
    (ifSucc: PartialFunction[(T, Reader[Char]), Any]) => {
      p(new CharSequenceReader(toTest)) match {
        case main.Success(value, rest) if ifSucc.isDefinedAt(value -> rest) =>
          ifSucc(value -> rest)
        case t => failure(s"not parsed, received: $t")
      }
      success
    }
  }

  //  val x = {
  //    case (v:URL,i:main.Input) => println("fdsfs")
  ////    case (v:OnlyView,i:main.Input) => println("kopkop")
  //  }

}


object links {
  val twoWpt_oneBetween_firstAndSecond = "https://www.google.pl/maps/dir/50.0678889,19.9024068/50.0712632,19.8950682/@50.0716396,19.8895299,15z/data=!4m9!4m8!1m5!3m4!1m2!1d19.9036674!2d50.0767216!3s0x47165bb65b7bf787:0x518bd8fa107baf09!1m0!3e0"
  val twoWpt_nothingBetween = "https://www.google.pl/maps/dir/50.0733015,19.9052391/50.0661121,19.9122344/@50.0678889,19.9024068,15z/data=!4m2!4m1!3e0"
  /*
  https://www.google.pl/maps/dir/
  50.0678889,19.9024068/
  50.0712632,19.8950682/
  @50.0716396,19.8895299,15z/data=
  !4m9!4m8
  !1m5!3m4!1m2!1d19.9036674!2d50.0767216!3s0x47165bb65b7bf787:0x518bd8fa107baf09
  !1m0
  !3e0
  */
}