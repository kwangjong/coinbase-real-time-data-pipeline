package producer;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

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

        // Create a WebSocket client.
        WebSocketClient client = new WebSocketClient(new URI("ws://abc.org/websocket")) {
            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("WebSocket connection opened");
            }

            @Override
            public void onMessage(String message) {
                // Produce the message to Kafka.
                producer.send(new ProducerRecord<>(TOPIC, message));
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket connection closed");
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("WebSocket error: " + ex.getMessage());
            }
        };
        // Connect the WebSocket client.
        client.connect();

        // Keep the producer running until the user presses Enter.
        System.in.read();

        // Close the producer and WebSocket client.
        producer.close();
        client.close();
    }
}
