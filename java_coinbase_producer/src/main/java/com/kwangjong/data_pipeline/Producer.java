package com.kwangjong.data_pipeline;

import java.io.IOException;
import java.net.URISyntaxException;
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
    private static final String BOOTSTRAP_SERVERS = "kafka:9092";
    private static final String PRODUCT_IDS = "[\"ETH-USD\",\"BTC-USD\",\"XRP-USD\"]";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Create a Kafka producer.
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Create a URI object
        URI coinbase_uri = null;
        try{
            coinbase_uri = new URI("wss://ws-feed.exchange.coinbase.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Create a WebSocket client.
        WebSocketClient client = new WebSocketClient(coinbase_uri) {
            private boolean subscribed = false;
            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("WebSocket connection opened");
                send("{ \"type\": \"subscribe\", \"product_ids\": " + PRODUCT_IDS + ", \"channels\": [\"ticker\"] }");
            }

            @Override
            public void onMessage(String message) {
                if (!subscribed) {
                    if (message.substring(9,14).equals("error")) {
                        System.out.println("Subscription failed: " + message);
                        producer.close();
                        close();
                    } else {
                        subscribed = true;
                    }
                } else {
                    // Produce the message to Kafka.
                    producer.send(new ProducerRecord<>(TOPIC, message));
                }
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
