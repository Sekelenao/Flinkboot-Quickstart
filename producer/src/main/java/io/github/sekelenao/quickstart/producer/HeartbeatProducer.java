package io.github.sekelenao.quickstart.producer;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.sekelenao.quickstart.pojo.DeviceStatus;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;

public class HeartbeatProducer {

    private static final Logger log = LoggerFactory.getLogger(HeartbeatProducer.class);

    public static void main(String[] args) throws Exception {
        var bootstrapServers = args.length > 0 ? args[0] : "localhost:9092";
        var topic = args.length > 1 ? args[1] : "heartbeats.raw";
        var messageCount = args.length > 2 ? Integer.parseInt(args[2]) : 15;
        var intervalMs = args.length > 3 ? Long.parseLong(args[3]) : 500L;

        var props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        var mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();

        var random = new Random();
        var devices = new String[]{"srv-alpha-01", "srv-beta-02", "srv-gamma-03", "edge-gateway-10"};

        log.info("Sending {} heartbeats to topic '{}' on '{}' (interval: {}ms)...",
                messageCount, topic, bootstrapServers, intervalMs);

        try (var producer = new KafkaProducer<String, String>(props)) {
            for (var i = 1; i <= messageCount && !Thread.currentThread().isInterrupted(); i++) {
                var deviceId = devices[random.nextInt(devices.length)];
                var roll = random.nextInt(100);

                DeviceStatus status;
                Duration latency;

                if (roll < 60) {
                    status = DeviceStatus.HEALTHY;
                    latency = Duration.ofMillis(50 + random.nextInt(150));
                } else if (roll < 80) {
                    status = DeviceStatus.HEALTHY;
                    latency = Duration.ofMillis(600 + random.nextInt(800)); // High latency (> 500ms)
                } else if (roll < 92) {
                    status = DeviceStatus.DEGRADED;
                    latency = Duration.ofMillis(300 + random.nextInt(400));
                } else {
                    status = DeviceStatus.DOWN;
                    latency = Duration.ofMillis(2000);
                }

                var event = new HeartbeatEvent();
                event.deviceId = deviceId;
                event.status = status;
                event.timestamp = LocalDateTime.now();
                event.latency = latency;

                var json = mapper.writeValueAsString(event);

                final var currentMsg = i;
                producer.send(new ProducerRecord<>(topic, deviceId, json), (metadata, exception) -> {
                    if (exception != null) {
                        log.error("[{}/{}] Failed to send heartbeat for {}", currentMsg, messageCount, deviceId, exception);
                    } else {
                        log.info("[{}/{}] Sent heartbeat: device={} status={} latency={}ms",
                                currentMsg, messageCount, deviceId, status, latency.toMillis());
                    }
                });

                Thread.sleep(intervalMs);
            }
            producer.flush();
        }

        log.info("✅ Finished sending {} heartbeats. Producer stopped cleanly.", messageCount);
    }
}
