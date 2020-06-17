package example
import scala.io.Source

object Hello extends Misc with App {
  // SBT - это больно https://github.com/sbt/sbt/issues/3963
  val filename = System.getProperty("user.dir") + "/src/main/resources/SAT__College_Board__2010_School_Level_Results.csv"
  val lines = readFile(filename)

  val res = lines.drop(1)
  .map(getValues(_))
  .fold((0,0,0,0))((a, b) => calc(a, b))

  println("Critical Reading Mean %f, Mathematics Mean %f, Writing Mean %f".format(
    res._2/res._1.toFloat, res._3/res._1.toFloat, res._4/res._1.toFloat))
}

trait Misc {
  def readFile(filename: String): Seq[String] = {
      val bufferedSource = io.Source.fromFile(filename)
      val lines = (for (line <- bufferedSource.getLines()) yield line).toList
      bufferedSource.close
      lines
  }
  def hasValues(line:String): Boolean = {
    val values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")
    values.length > 2
  }

  def getValues(line:String): (Int, Int, Int, Int) = {
    val values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")
    if (values.length > 2)
      (values(2).toInt, values(3).toInt, values(4).toInt, values(5).toInt)
    else
      (0, 0, 0, 0)
  }

  // в F# это выглядело бы вот так:
  // let calc acc i = (acc.1 + i.1, acc.2 + i.2 * i.1, acc.3 + i.3 * i.1, acc.4 + i.4 * i.1)
  // без явного указания типов
  def calc(acc:(Int,Int,Int,Int), i:(Int,Int,Int,Int)): (Int, Int, Int, Int) = {
    (acc._1 + i._1, acc._2 + i._2 * i._1, acc._3 + i._3 * i._1, acc._4 + i._4 * i._1)
  }
}
