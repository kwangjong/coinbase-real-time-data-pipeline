FROM ubuntu:22.04
RUN apt-get update; apt-get install -y wget openjdk-11-jdk

# HDFS
RUN wget https://dlcdn.apache.org/hadoop/common/hadoop-3.4.0/hadoop-3.4.0.tar.gz; tar -xf hadoop-3.4.0.tar.gz; rm hadoop-3.4.0.tar.gz

ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH="${PATH}:/hadoop-3.4.0/bin"
ENV HADOOP_HOME=/hadoop-3.4.0