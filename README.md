# coinbase-real-time-data-pipeline

## Project Overview
The Coinbase Real-time Data Pipeline is designed to retrieve real-time cryptocurrency price data from the Coinbase API. It leverages a combination of technologies, including Kafka for data queuing, Apache Spark for data processing, Cassandra for data storage, and Grafana for data visualization. This comprehensive pipeline is containerized using Docker for easy deployment and is currently set up for Minikube using Kubernetes. With minimal adjustments, it can be seamlessly transitioned to run on actual Kubernetes clusters, such as GKE or EKE, for scalability and production use.

## Screenshot

![plot](https://i.imgur.com/vSGW4js.png)

## Architecture

![architecture](https://i.imgur.com/w4dNGpx.png)

The real-time data pipeline project facilitates the collection, processing, storage, and visualization of cryptocurrency market data from Coinbase. It comprises key components:

- **Coinbase WebSocket API**: This serves as the initial data source, providing real-time cryptocurrency market data streams, including trades and price changes.
- **Java Kafka Producer Microservice**: To efficiently manage data, a Java-based microservice functions as a Kafka producer. It collects data from the Coinbase WebSocket API and sends it to the Kafka broker.
- **Kafka Broker**: Kafka, an open-source distributed event streaming platform, forms the core of the data pipeline. It efficiently handles high-throughput, fault-tolerant, real-time data streams, receiving data from the producer and making it available for further processing.
- **Go Kafka Consumer**: Implemented in Go, the Kafka consumer pulls raw data from Kafka topics and stores them directly into HDFS (Hadoop Distributed File System). This step ensures robust and scalable storage of raw data.
- **HDFS for Raw Data Storage**: HDFS provides reliable storage for large volumes of raw data. It is ideal for streaming data applications due to its fault tolerance, high throughput, and scalability. The Go Kafka consumer writes raw data directly to HDFS, enabling seamless integration into the pipeline.
- **Spark Structured Streaming for Data Processing**: Apache Spark, a powerful in-memory data processing framework, is chosen for real-time data processing. With Spark Structured Streaming, real-time transformations and computations are applied to incoming data streams, ensuring data is ready for storage.
- **Cassandra Database**: For long-term data storage, Apache Cassandra, a highly scalable NoSQL database known for its exceptional write and read performance, is employed. Cassandra serves as the solution for storing historical cryptocurrency market data.
- **Grafana for Data Visualization**: To make data easily understandable, Grafana, an open-source platform for monitoring and observability, is utilized. Grafana queries data from Cassandra to create compelling real-time visualizations, providing insights into cryptocurrency market trends.

## Deployment
<!--
![kubernetes-pods](https://i.imgur.com/LacnL5c.png)
-->

To ensure the smooth orchestration and management of all project components on a single machine, Kubernetes comes into play. Leveraging Minikube, a lightweight Kubernetes distribution tailored for local development and testing, we can simulate a production-like environment right on our local system. This deployment approach simplifies the process of setting up and experimenting with our real-time data pipeline. Moreover, with minor adjustments, this configuration can be smoothly migrated to actual Kubernetes clusters, like GKE or EKE, offering scalability and making it production-ready.

## CI/CD
<p align="center">
  <img src="https://i.imgur.com/LU2iYUF.png" style="width: 600px"/>
</p>

This repository leverages GitHub Actions for automated CI/CD. Upon each code push to the main branch, Docker images are automatically built and pushed to Docker Hub. This ensures that the containers remain up to date and ready for deployment.

## Blog posts
* [Real-Time Data Pipeline Architecture Overview: Kafka, Spark, and Cassandra](https://kwangjong.github.io/blog/2023-09-22-Real-Time-Data-Pipeline-Architecture-Overview:-Kafka,-Spark,-and-Cassandra)
* [Getting Started with Apache Kafka](https://kwangjong.github.io/blog/2023-09-24-Getting-Started-with-Apache-Kafka)
* [Structured Stream Processing with Scala Spark](https://kwangjong.github.io/blog/2023-09-25-Structured-Stream-Processing-with-Scala-Spark)
* [Storing processed data in Cassandra](https://kwangjong.github.io/blog/2023-09-26-Storing-processed-data-in-Cassandra)
* [Visualizing Stream Data in Cassandra Using Grafana](https://kwangjong.github.io/blog/2023-10-02-Visualizing-Stream-Data-in-Cassandra-Using-Grafana)
* [Deploying a Real-time Data Pipeline in Minikube with Kubernetes](https://kwangjong.github.io/blog/2023-10-04-Deploying-a-Real-time-Data-Pipeline-in-Minikube-with-Kubernetes)
* [Automating Docker Image Builds with GitHub Actions: CI/CD](https://kwangjong.github.io/blog/2023-10-04-Automating-Docker-Image-Builds-with-GitHub-Actions:-CICD)
* [Transforming Data Pipelines with HDFS: A Scalable Solution for Raw Data Storage](https://kwangjong.github.io/blog/2024-06-20-Transforming-Data-Pipelines-with-HDFS:-A-Scalable-Solution-for-Raw-Data-Storage)
* [Enhancing Real-Time Data Pipelines with Kafka and HDFS in Go](https://kwangjong.github.io/blog/2024-06-23-Enhancing-Real-Time-Data-Pipelines-with-Kafka-and-HDFS-in-Go)

## Future Work
* Perform code cleanup and integration testing
* Explore interesting data analysis opportunities on the collected data
