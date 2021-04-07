package main

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

object BostonGuide {
  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      println("Provide crime.csv, offence_codes.csv for BostonGuide and path to save parquet file")
      sys.exit(-1)
    }
    val spark = SparkSession.builder()
      .appName("BostonGuide")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")

    val save_path = args(2) + "BostonGiude.parquet"

    val crimes = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(args(0))

    val of_codes = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(args(1))
      .dropDuplicates("CODE")

    val raw = crimes.join(
      broadcast(of_codes),
      crimes("OFFENSE_CODE") <=> of_codes("CODE")
      )
      .drop(
        "INCIDENT_NUMBER",
        "OFFENSE_CODE",
        "OFFENSE_CODE_GROUP",
        "OFFENSE_DESCRIPTION",
        "REPORTING_AREA",
        "SHOOTING",
        "OCCURRED_ON_DATE",
        "DAY_OF_WEEK",
        "HOUR",
        "UCR_PART",
        "STREET",
        "Location",
        "CODE"
      )
      .withColumn("district", col("DISTRICT"))
      .na.drop(Seq("district"))

    val median = raw
      .drop(
        "Lat",
        "Long",
        "NAME"
      )
      .groupBy("district", "YEAR", "MONTH")
      .agg(
        count("YEAR") as "CNT",
      )
      .groupBy("district")
      .agg(
        sum("CNT") as "crimes_total",
        percentile_approx(col("CNT"), lit(0.5), lit(100)) as "crimes_monthly"
      )

    def windowSpec = Window.partitionBy("district").orderBy(desc("count"))

    val most_frequent = raw
      .drop("MONTH", "YEAR", "Lat", "Long")
      .withColumn("NAME", split(col("NAME"), " - ")(0))
      .groupBy("district", "NAME")
      .count()
      .withColumn("rowNum", row_number().over(windowSpec))
      .filter("rowNum <= 3")
      .orderBy("district", "rowNum")
      .groupBy("district")
      .agg(
        collect_list("NAME") as "frequent_crime_types"
      )
      .withColumn("frequent_crime_types", concat_ws(", ", col("frequent_crime_types")))

    val location = raw
      .groupBy("district")
      .agg(
        avg("Lat") as "lat",
        avg("Long") as "long"
      )

    val res = median.join(
        broadcast(most_frequent),
        Seq("district")
      )
      .join(
        broadcast(location),
        Seq("district")
      )

    res.write.mode("overwrite").parquet(save_path)

    val test = spark.read.parquet(save_path)

    test.show(100, false)

    spark.stop()
  }

}
