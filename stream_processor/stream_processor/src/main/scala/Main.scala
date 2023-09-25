object CoinbaseStreamProcessorApp extends App {

    import org.apache.spark.sql._
    import org.apache.spark.sql.functions._
    import org.apache.spark.sql.types._
    import org.apache.spark.sql.streaming.{OutputMode, Trigger}
    import org.apache.spark.sql.expressions.Window
    //import org.apache.spark.sql.cassandra._

    // Define the schema for the JSON data
    val schema = StructType(Seq(
        StructField("sequence", LongType, nullable = false),
        StructField("product_id", StringType, nullable = false),
        StructField("price", StringType, nullable = false),
        StructField("low_24h", StringType, nullable = false),
        StructField("high_24h", StringType, nullable = false),
        StructField("time", TimestampType, nullable = false)
    ))

    // Create a SparkSession
    val spark = SparkSession.builder
    .appName("coinbaseStreamProcessor")
    .config("spark.master", "local")
    //.config("spark.cassandra.connection.host", "localhost") // Cassandra connection settings
    .getOrCreate()

    // Read data from Kafka
    val kafkaStream = spark.readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092") // Kafka broker address
    .option("subscribe", "test-topic") // Kafka topic to subscribe to
    .load()

    // Parse JSON and select relevant fields
    val jsonStream = kafkaStream
    .selectExpr("CAST(value AS STRING)") // Assume the JSON data is in the 'value' field
    .select(from_json(col("value"), schema).as("data"))
    .select("data.*")

    val parsedDF = jsonStream
    .select(
        col("price").cast(FloatType).alias("price"),
        col("low_24h").cast(FloatType).alias("age"),
        col("high_24h").cast(FloatType).alias("age")
    )

    // Calculate the 1-hour moving average of the "price" field using a time-based window
    val movingAvgStream = jsonStream
    .withWatermark("time", "10 second") // Set a watermark to handle late data
    .groupBy(window(col("time"), "10 second"), col("product_id"))
    .agg(avg("price").alias("moving_average"))

    // Start the query to write the results to the console
    val query = jsonStream
    .writeStream
    .outputMode("append")
    .format("console")
    .start()

    query.awaitTermination()
}