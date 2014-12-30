import io.github.morgaroth.navigator_import.core.build.BuildInfo
import ala.Test

object TestClass {
  def main(args: Array[String]): Unit = {
    import BuildInfo._
    println(s"$sbtVersion")
    println("ala ma kota")
    println(new Test())
  }

  def dupa = ""
}
