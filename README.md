# coinbase-real-time-data-pipeline

## Project Overview
The Coinbase Real-time Data Pipeline is designed to retrieve real-time cryptocurrency price data from the Coinbase API. It leverages a combination of technologies, including Kafka for data queuing, Apache Spark for data processing, Cassandra for data storage, and Grafana for data visualization. This comprehensive pipeline is containerized using Docker for easy deployment and is currently set up for Minikube using Kubernetes. With minimal adjustments, it can be seamlessly transitioned to run on actual Kubernetes clusters, such as GKE or EKE, for scalability and production use.

## Screenshot

![plot](https://i.imgur.com/vSGW4js.png)

## Architecture
![architecture](https://i.imgur.com/Be7RcI2.jpeg)

## Blog posts
* [Real-Time Data Pipeline Architecture Overview: Kafka, Spark, and Cassandra](https://kwangjong.github.io/blog/2023-09-22-Real-Time-Data-Pipeline-Architecture-Overview:-Kafka,-Spark,-and-Cassandra)
* [Getting Started with Apache Kafka](https://kwangjong.github.io/blog/2023-09-24-Getting-Started-with-Apache-Kafka)
* [Structured Stream Processing with Scala Spark](https://kwangjong.github.io/blog/2023-09-25-Structured-Stream-Processing-with-Scala-Spark)
* [Storing processed data in Cassandra](https://kwangjong.github.io/blog/2023-09-26-Storing-processed-data-in-Cassandra)
* [Visualizing Stream Data in Cassandra Using Grafana](https://kwangjong.github.io/blog/2023-10-02-Visualizing-Stream-Data-in-Cassandra-Using-Grafana)
* [Deploying a Real-time Data Pipeline in Minikube with Kubernetes](https://kwangjong.github.io/blog/2023-10-04-Deploying-a-Real-time-Data-Pipeline-in-Minikube-with-Kubernetes)

## Todo
* ~~Implement container orchestration using Kubernetes -> write a blog post on this~~
* ~~Set up a CI pipeline using GitHub Actions~~ -> write a blog post on this
* Perform code cleanup and integration testing
* Explore interesting data analysis opportunities on the collected data
