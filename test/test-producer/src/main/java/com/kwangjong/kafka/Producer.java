package com.kwangjong.kafka;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {

    private static final String TOPIC = "test-topic";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Create a Kafka producer.
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Send mock data every second.
        for (int i=0; i<10; i++) {
            producer.send(new ProducerRecord<>(TOPIC, "mock_data"));
            System.out.printf("sent %d\n", i);
            TimeUnit.SECONDS.sleep(1);
        }

        // Close the producer.
        producer.close();
    }
}
