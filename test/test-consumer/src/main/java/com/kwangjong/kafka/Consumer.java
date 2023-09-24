package com.kwangjong.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class Consumer {

    public static void main(String[] args) {
        // Create a Kafka consumer.
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka-service:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the Kafka topic.
        consumer.subscribe(Arrays.asList("test-topic"));

        try {
            while(true) {
                // Poll for new messages.
                ConsumerRecords<String, String> records = consumer.poll(100);
                // Print the messages.
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Received message: " + record.value());
                }
            }
        } finally {
            // Close the consumer.
            consumer.close();
        }
    }
}
