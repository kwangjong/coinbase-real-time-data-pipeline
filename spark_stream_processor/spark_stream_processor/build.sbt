val scala3Version = "2.13.12"
val sparkVersion = "3.5.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "stream_processor",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
        "org.apache.spark" %% "spark-core" % sparkVersion,
        "org.apache.spark" %% "spark-sql" % sparkVersion,
        "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion
    ),
    
    fork := true,
    javaOptions += "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED"
  )
