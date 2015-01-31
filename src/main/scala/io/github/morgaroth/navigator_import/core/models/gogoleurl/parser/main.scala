package io.github.morgaroth.navigator_import.core.models.gogoleurl.parser


/*
!3m1!4b1!4m2!4m1!3e0 /3 puonkty
!3m1!4b1!4m2!4m1!3e0
!3m1!4b1!4m2!4m1!3e0 /inna kolejność
!3m1!4b1!4m2!4m1!3e0
!3m1!4b1!4m2!4m1!3e0 /2 ounkty
        !4m2!4m1!3e0 /4 punkty
        !4m2!4m1!3e0 //te same inna kolejność
        !4m2!4m1!3e0
		!4m2!4m1!3e0 / 5 punktów
		!4m2!4m1!3e0 / 3 punkty ale jakoś inaczej

========================================================================
2 punkty  dwa dodatkowe pomiędzy
!4m14!4m13!
1m10
!3m4!1m2!1d19.8981254!2d50.0731114!3s0x47165bb80ea7f589:0xb3e455a546d1c5de
!3m4!1m2!1d19.9116772!2d50.0727898!3s0x47165bb0698ee1b1:0xda653b003dde16a5
!1m0
!3e0

==============================================================================
3 punkty, jeden dodatkowy pomiędzy 2 a 3
!4m10!4m9
!1m0
!1m5
!3m4!1m2!1d19.912071!2d50.0738267!3s0x47165bb049503f93:0x55151a19c0da699b
!1m0
!3e0

===========================================================================
3 punkty, jeden dodatkowy pomiędzy 2 a 3 i jeden dodatkowy pomiędzy 1 a 2
!4m15!4m14
!1m5
!3m4!1m2	!1d19.9022842!2d50.0730827	!3s0x47165bb77d52e61d:0x9ea76bd59cd3c270
!1m5
!3m4!1m2	!1d19.912071!2d50.0738267 	!3s0x47165bb049503f93:0x55151a19c0da699b
!1m0
!3e0

==================================================================================
3 punkty, dwa dodatkowe pomiędzy 1 a 2 i jeden dodatkowy pomiędzy 2 a 3
!4m20!4m19
!1m10
!3m4!1m2	!1d19.897585!2d50.0732477	!3s0x47165bb80ea7f589:0xb3e455a546d1c5de
!3m4!1m2	!1d19.9022842!2d50.0730827	!3s0x47165bb77d52e61d:0x9ea76bd59cd3c270
!1m5
!3m4!1m2	!1d19.912071!2d50.0738267	!3s0x47165bb049503f93:0x55151a19c0da699b
!1m0
!3e0

==============================================================
3 punkty, trzy dodatkowe międyz pierwszym i drugim


https://www.google.pl/maps/dir/50.0747783,19.8923216/50.0687461,19.9221049/50.0558804,19.924079/@50.0624424,19.9185517,14z/data=


!4m20!4m19
!1m15
!3m4!1m2!1d19.881231!2d50.064604!3s0x47165be9bc11d5c1:0x90578637966ea595
!3m4!1m2!1d19.905905!2d50.0749938!3s0x47165bb72066d70d:0x86f5838a897e1432
!3m4!1m2!1d19.9176716!2d50.706999!3s0x47165baf63aaf197:0xef2428fe9d78d5cf
!1m0
!1m0

!3e0

================================================================
3 punkty, + trzy między pierwszym i drugim,
+ dwa między drugim i trzecim

https://www.google.pl/maps/dir/50.0747783,19.8923216/50.0687461,19.9221049/50.0558804,19.924079/@50.0624424,19.9185517,14z/data=

!4m30!4m29
!1m15
!3m4!1m2!1d19.881231!2d50.064604!3s0x47165be9bc11d5c1:0x90578637966ea595
!3m4!1m2!1d19.905905!2d50.0749938!3s0x47165bb72066d70d:0x86f5838a897e1432
!3m4!1m2!1d19.9176716!2d50.0706999!3s0x47165baf63aaf197:0xef2428fe9d78d5cf
!1m10
!3m4!1m2!1d19.9285717!2d50.0634694!3s0x47165b0963a0cdc3:0xe61609c64dfb5607
!3m4!1m2!1d19.9339983!2d50.0551194!3s0x47165b6d648200d3:0xecfbd9a830e98320
!1m0

!3e0

źródło:
https://www.google.pl/maps/dir/50.0747783,19.8923216/50.0687461,19.9221049/50.0558804,19.924079/@50.0624424,19.9185517,14z/data=!4m30!4m29!1m15!3m4!1m2!1d19.881231!2d50.064604!3s0x47165be9bc11d5c1:0x90578637966ea595!3m4!1m2!1d19.905905!2d50.0749938!3s0x47165bb72066d70d:0x86f5838a897e1432!3m4!1m2!1d19.9176716!2d50.0706999!3s0x47165baf63aaf197:0xef2428fe9d78d5cf!1m10!3m4!1m2!1d19.9285717!2d50.0634694!3s0x47165b0963a0cdc3:0xe61609c64dfb5607!3m4!1m2!1d19.9339983!2d50.0551194!3s0x47165b6d648200d3:0xecfbd9a830e98320!1m0!3e0


wnioski:
 zaobserowowana sekwencja:
!4mX!4m(x-1)
(
!1m[(ilość punktów poniędzy węzłem aktualnym a następnym)-1]*5
węzły pośrednie: !3m4!1m2!1dLATITUDE!2dLONGITUDE!3sDZIWNYHASH
) *
!3e0



https://www.google.pl/maps/place/Urz%C4%85d+Kontroli+Skarbowej/@50.078709,19.928339,17z/data=

!3m1!4b1!4m42!1m39!4m38!1m18!2m2!1d19.8923216!2d50.0747783!3m4!1m2!1d19.881231!2d50.064604!3s0x47165be9bc11d5c1:0x90578637966ea595
!3m4!1m2!1d19.905905!2d50.0749938!3s0x47165bb72066d70d:0x86f5838a897e1432
!3m4!1m2!1d19.9176716!2d50.0706999!3s0x47165baf63aaf197:0xef2428fe9d78d5cf
!1m13!2m2!1d19.9221049!2d50.0687461
!3m4!1m2!1d19.9285717!2d50.0634694!3s0x47165b0963a0cdc3:0xe61609c64dfb5607
!3m4!1m2!1d19.9339983!2d50.0551194!3s0x47165b6d648200d3:0xecfbd9a830e98320
!1m3!2m2!1d19.92128!2d50.0553739!3e0!3m1!1s0x47165bab4842f443:0x172599eb1c5e08a5


3 punkty


https://www.google.pl/maps/dir/
Zarzecze+66,+33-332+Krak%C3%B3w/
50.0687461,19.9221049/
50.0558804,19.924079/

@24.7000862,-71.1124163,3z/data=

!4m20!4m19
!1m5
!1m1!1s0x47165bc60351781d:0x310b92be86d1c04f
!2m2!1d19.8922409!2d50.0747409
!1m10
!3m4!1m2!1d19.9285717!2d50.0634694!3s0x47165b0963a0cdc3:0xe61609c64dfb5607
!3m4!1m2!1d19.9339983!2d50.0551194!3s0x47165b6d648200d3:0xecfbd9a830e98320
!1m0
!3e0


*/

sealed trait Wpt

case class NormalWpt(longitude: Double, latitude: Double) extends Wpt

case class WordsWpt(body: String) extends Wpt

case class AnonWpt(wpt: NormalWpt, hexValue: (String, String))

case class URL(waypoints: List[Wpt], anonymousWpts: List[List[AnonWpt]], zoom: Int)


import scala.util.parsing.combinator._

object main extends RegexParsers {

  val begin: Parser[String] = "https://www.google.pl/maps/dir/"
  val itudeParser: Parser[Double] = """(-?\d{1,3}\.\d{5,8})""".r ^^ (_.toDouble)
  val stdWpt: Parser[NormalWpt] = itudeParser ~ "," ~ itudeParser ^^ (x => NormalWpt(x._1._1, x._2))
  // Zarzecze+66,+33-332+Krak%C3%B3w/
  val wordWpt: Parser[WordsWpt] = """[^/]+""".r ^^ (x => WordsWpt(x))
  val mapCenter = "@" ~> stdWpt
  val zoom = """\d{1,2}""".r ^^ (_.toInt)
  val wpt: Parser[Wpt] = stdWpt | wordWpt
  val wpts: Parser[List[Wpt]] = wpt ~ rep("/" ~> wpt) ^^ (x => x._1 :: x._2)

  val hex: Parser[String] = """0x[0-9a-f]{16}""".r
  val anonWpt = "!3m4!1m2" ~> ("!1d" ~> itudeParser) ~ ("!2d" ~> itudeParser) ~ ("!3s" ~> hex) ~ (":" ~> hex) ^^ {
    x => AnonWpt(NormalWpt(x._1._1._1, x._1._1._2), x._1._2 -> x._2)
  }
  val anonWptsBlock = """1m(\d{1,3})""".r ^^ (_.toInt)
  val urlParser = begin ~> wpts

}

/*



https://www.google.pl/maps/dir/

50.0706848,19.8773442/
50.0628614,19.8879443/
50.0695279,19.9072562/

@50.0645605,19.8828224,15z/data=

!4m25!4m24

!1m5
!3m4!1m2!1d19.8727851!2d50.0625734!3s0x47165be60ec99557:0xc8bd4619b465c08b
!1m15
!3m4!1m2!1d19.9052557!2d50.0564737!3s0x47165b9a2632f08f:0x4cb2b2e873e1ce95
!3m4!1m2!1d19.9214748!2d50.0685078!3s0x47165ba6399bcd71:0x4ce4cf70b2011029
!3m4!1m2!1d19.9134191!2d50.0642029!3s0x47165ba24a0219ef:0x3674d5a82eb082b4
!1m0
!3e0

 */



