package example
import scala.io.Source

object Hello extends Misc with App {
  // SBT - это больно https://github.com/sbt/sbt/issues/3963
  val filename = System.getProperty("user.dir") + "/src/main/resources/SAT__College_Board__2010_School_Level_Results.csv"
  val lines = readFile(filename)
}

trait Misc {
  def readFile(filename: String): Seq[String] = {
      val bufferedSource = io.Source.fromFile(filename)
      val lines = (for (line <- bufferedSource.getLines()) yield line).toList
      bufferedSource.close
      lines
  }
}
