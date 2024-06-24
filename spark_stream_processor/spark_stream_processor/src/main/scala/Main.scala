object StreamProcessorApp extends App {

    import org.apache.spark.sql._
    import org.apache.spark.sql.functions._
    import org.apache.spark.sql.types._
    import org.apache.spark.sql.streaming.{OutputMode, Trigger}
    import org.apache.spark.sql.expressions.Window

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
    .appName("StreamProcessor")
    .config("spark.master", "local")
    .config("spark.cassandra.connection.host", "cassandra-service")
    .config("spark.cassandra.connection.port", "9042")
    .getOrCreate()

    // Read data from Kafka
    val kafkaStream = spark.readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "kafka-service:9092") // Kafka broker address
    .option("subscribe", "coin-data") // Kafka topic to subscribe to
    .option("kafka.group.id", "coin-data-consumer") // Kafka consumer group ID
    .load()

    // Parse JSON and select relevant fields
    val jsonStream = kafkaStream
    .selectExpr("CAST(value AS STRING)") // Assume the JSON data is in the 'value' field
    .select(from_json(col("value"), schema).as("data"))
    .select("data.*")

    val parsedStream = jsonStream
    .select(
        col("sequence"),
        col("product_id"),
        col("price").cast(FloatType).alias("price"),
        col("low_24h").cast(FloatType).alias("low_24h"),
        col("high_24h").cast(FloatType).alias("high_24h"),
        col("time")
    )

    // Write the results to Cassandra
    parsedStream
    .writeStream
    .foreachBatch { (batchDF: DataFrame, batchId: Long) =>
         batchDF
             .write
             .format("org.apache.spark.sql.cassandra")
             .options(Map("table" -> "coinbase_prices", "keyspace" -> "coinbase")) // Cassandra table and keyspace names
             .mode("append")
             .save()
    }
    .trigger(Trigger.ProcessingTime("5 seconds"))
    .outputMode(OutputMode.Append())
    .start()
    .awaitTermination()
}
