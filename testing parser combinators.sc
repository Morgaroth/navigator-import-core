import io.github.morgaroth.navigator_import.core.models.googleurl.parser.{WordsWpt, NoWpt, BaseWpt, Wpt}

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object test extends RegexParsers{

  val str = "50.0712632,19.8950682//@50.0678889,19.9024068"

  val itudeParser: Parser[Double] = """(-?\d{1,3}\.\d{5,8})""".r ^^ (_.toDouble)
  val stdWpt: Parser[Wpt] = itudeParser ~ "," ~ itudeParser ^^ (x => Wpt(x._1._1, x._2))
  val wordWpt: Parser[WordsWpt] = """[^/@]+""".r ^^ (x => WordsWpt(x))
  val mapCenter: Parser[Wpt] = "@" ~> stdWpt
  val noWpt: Parser[NoWpt.type] = guard(not("@")) ~ """""".r ^^ (x => NoWpt)
  val zoom: Parser[Int] = ("""\d{1,2}""".r ^^ (_.toInt)) <~ "z"
  val wpt: Parser[BaseWpt] = stdWpt | wordWpt
  val wpts: Parser[List[BaseWpt]] = (wpt | noWpt) ~ rep("/" ~> (wpt | noWpt)) ^^ (x => x._1 :: x._2)

}

import test.str
import io.github.morgaroth

test.wpts(new CharSequenceReader(str)).map(_.size)
