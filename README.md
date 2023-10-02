# coinbase-real-time-data-pipeline

## Project Overview
The Coinbase Real-time Data Pipeline is engineered to acquire real-time cryptocurrency price data from the Coinbase API. It employs Apache Spark for data processing, Cassandra for data storage, and Grafana for data visualization. This robust pipeline is containerized using Docker for seamless deployment and utilizes Kafka as a message broker to ensure low latency, scalability, and high availability.

## Architecture
![architecture](https://i.imgur.com/Be7RcI2.jpeg)

## Blog posts
* [Real-Time Data Pipeline Architecture Overview: Kafka, Spark, and Cassandra](https://kwangjong.github.io/blog/2023-09-22-Real-Time-Data-Pipeline-Architecture-Overview:-Kafka,-Spark,-and-Cassandra)
* [Getting Started with Apache Kafka](https://kwangjong.github.io/blog/2023-09-24-Getting-Started-with-Apache-Kafka)
* [Structured Stream Processing with Scala Spark](https://kwangjong.github.io/blog/2023-09-25-Structured-Stream-Processing-with-Scala-Spark)
* [Storing processed data in Cassandra](https://kwangjong.github.io/blog/2023-09-26-Storing-processed-data-in-Cassandra)
* [Visualizing Stream Data in Cassandra Using Grafana](https://kwangjong.github.io/blog/2023-10-02-Visualizing-Stream-Data-in-Cassandra-Using-Grafana)

## Todo
* ~~Implement container orchestration using Kubernetes~~ -> write a blog post on this
* Set up a CI pipeline using GitHub Actions
* Perform code cleanup and integration testing
* Explore interesting data analysis opportunities on the collected data
