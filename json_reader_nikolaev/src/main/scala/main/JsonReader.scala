package main

import org.apache.spark.{SparkConf, SparkContext}
import io.circe.generic.auto._
import io.circe.parser.decode


object JsonReader {
  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("Provide path to winemag-data-130k-v2.json")
      sys.exit(-1)
    }
    case class VineDetails(
                            id: Option[Int],
                            country: Option[String],
                            points: Option[Int],
                            title: Option[String],
                            variety: Option[String],
                            winery: Option[String]
                          )

    val json_path = args(0)

    val conf = new SparkConf().setAppName("JsonReader")
    val sc = new SparkContext(conf)

    val text = sc.textFile(json_path)
    text
      .map(decode[VineDetails](_))
      .foreach { l => println(l.toString) }

  }
}
