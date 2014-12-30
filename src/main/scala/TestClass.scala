import generated.info.BuildInfo

object TestClass {
  def main(args: Array[String]): Unit = {
    import BuildInfo._
    println(s"$sbtVersion $buildinfoBuildnumber")
  }
}
