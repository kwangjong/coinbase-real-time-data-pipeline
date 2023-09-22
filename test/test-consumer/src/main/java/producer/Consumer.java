import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {

    public static void main(String[] args) {
        // Create a Kafka consumer.
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the Kafka topic.
        consumer.subscribe(Arrays.asList("test-topic"));

        // Poll for new messages.
        ConsumerRecords<String, String> records = consumer.poll(100);

        // Print the messages.
        while (!records.isEmpty()) {
            ConsumerRecord<String, String> record = records.iterator().next();
            System.out.println("Received message: " + record.value());
        }

        // Close the consumer.
        consumer.close();
    }
}
