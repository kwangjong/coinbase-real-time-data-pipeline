package main

import (
	"context"
	"fmt"
	"log"

	"github.com/confluentinc/confluent-kafka-go/kafka"
	"github.com/colinmarc/hdfs"
)

func main() {
	// Kafka configuration
	config := kafka.ConfigMap{
		"bootstrap.servers": "kafka-service:9092",
		"group.id":          "hdfs-writer-group",
		"auto.offset.reset": "earliest",
	}

	// Create Kafka consumer
	consumer, err := kafka.NewConsumer(&config)
	if err != nil {
		log.Fatalf("Failed to create consumer: %s", err)
	}
	defer consumer.Close()

	// Subscribe to the Kafka topic
	topic := "coin-data"
	err = consumer.Subscribe(topic, nil)
	if err != nil {
		log.Fatalf("Failed to subscribe to topic: %s", err)
	}

	// HDFS configuration
	hdfsClient, err := hdfs.New("hdfs-service:9000")
	if err != nil {
		log.Fatalf("Failed to create HDFS client: %s", err)
	}

	// Open HDFS file for writing
	hdfsFilePath := "/raw_data"
	hdfsFile, err := hdfsClient.Append(hdfsFilePath)
	if err != nil {
		hdfsFile, err = hdfsClient.Create(hdfsFilePath)
		if err != nil {
			log.Fatalf("Failed to create HDFS file: %s", err)
		}
	}
	defer hdfsFile.Close()

	// Context to manage message consumption
	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	// Consume messages from Kafka
	for {
		select {
		case <-ctx.Done():
			fmt.Println("Stopping consumer")
			return
		default:
			msg, err := consumer.ReadMessage(-1)
			if err != nil {
				fmt.Printf("Consumer error: %v (%v)\n", err, msg)
				continue
			}

			// Write the raw data to HDFS
			_, err = hdfsFile.Write([]byte(msg.Value))
			if err != nil {
				log.Fatalf("Failed to write to HDFS file: %s", err)
			}

			// Print message to console (optional)
			fmt.Printf("Consumed message: %s\n", string(msg.Value))
		}
	}
}
