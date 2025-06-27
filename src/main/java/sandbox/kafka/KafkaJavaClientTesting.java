package sandbox.kafka;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import utils.DateTimeUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class KafkaJavaClientTesting {

    public static void main() throws InterruptedException {

        String topic = "my-first-topic";

        // produce messages
        List lyrics = List.of("Hello...", "it's me...", "i was wondering...", "if after all these years you'd like to meet",
                "to go over everything...", "They say that time's supposed to heal ya", "but I ain't done much healing",
                "Hello, can you hear me?", "I'm in California dreaming about who we used to be",
                "When we were younger and free", "I've forgotten how it felt before the world fell at our feet");
        WorkerProducer adele = new WorkerProducer("Adele", topic, lyrics);
        WorkerConsumer johnDoe = new WorkerConsumer("John DOE", topic);

        CompletableFuture<String> adeleAsyncWork = CompletableFuture.supplyAsync(() -> {
            adele.doWork(10);
            return adele.name + " finished producing!";
        });
        CompletableFuture<String> johnDoeAsyncWork = CompletableFuture.supplyAsync(() -> {
           johnDoe.doWork(1);
           return johnDoe.name + " finished consuming!";
        });


        CompletableFuture<Void> bothFutures = CompletableFuture.allOf(adeleAsyncWork, johnDoeAsyncWork);
        while (!bothFutures.isDone()) {
            System.out.println("[%s] Waiting for workers to finish work...".formatted(LocalDateTime.now()));
            Thread.sleep(1000);
        }
        System.out.println("All workers have finished!");
    }

    record WorkerProducer (String name, String topic, List<String> words) {
        @SneakyThrows
        public void doWork(int seconds) {
            System.out.println("[%s] just starting work that takes %s seconds".formatted(name, seconds));
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
            String key = "Adele";

            for (int i=0; i<seconds; i++) {
                System.out.println("[%s] %s (%d%%)".formatted(name, words.get(i), (int)((float)i/seconds *100)));
                producer.send(new ProducerRecord<>(topic, key, words.get(i)));
                Thread.sleep(1000);
            }
            System.out.println("[%s] finished producing!".formatted(name));
        }
    }

    record WorkerConsumer (String name, String topic) {
        @SneakyThrows
        public void doWork(int minutes) {
            System.out.println("[%s] just starting consuming messages for %d minute".formatted(name, minutes));

            // connect to kafka
            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "MyFirstConsumer");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

            // receive messages that were sent before the consumer started
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

            // create the consumer using props.
            try (final Consumer<Long, String> consumer = new KafkaConsumer<>(props)) {
                // subscribe to the topic.
                System.out.println("Consumer will pull messages from topic: " + topic);
                consumer.subscribe(List.of(topic));

                // poll messages from the topic and print them to the console
                consumer
                        .poll(Duration.ofMinutes(1))
                        .forEach(this::printReadable);
            }
            System.out.println("[%s] finished consuming!".formatted(name));
        }

        private void printReadable(ConsumerRecord<Long, String> record) {

            System.out.println(record + ", creationDateTime: " + timestampToDateString(record.timestamp()));
        }

        private String timestampToDateString(long timestamp) {
            return DateTimeUtils.formattedDateTimeFromTimestamp(timestamp, ZoneId.of("Europe/Paris"), DateTimeFormatter.ISO_DATE_TIME);
        }


    }
}
