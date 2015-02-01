import io.github.morgaroth.navigator_import.core.models.gogoleurl.parser._

import scala.util.parsing.input.CharSequenceReader

val x: PartialFunction[Result, Any] = {
  case v: URL if v.waypoints.size > 0 => println("fdsfs")
  case v: OnlyView => println("kopkop")
}
x.isDefinedAt(URL(List.empty, List.empty, 0))
x.isDefinedAt(URL(List(NoWpt), List.empty, 0))