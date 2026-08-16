package io.github.sekelenao.quickstart;

import io.github.sekelenao.flinkboot.core.api.Flinkboot;
import io.github.sekelenao.flinkboot.kafka.api.sink.KafkaSinkFactory;
import io.github.sekelenao.flinkboot.kafka.api.source.KafkaSourceFactory;
import io.github.sekelenao.quickstart.configuration.HeartbeatJobConfiguration;
import io.github.sekelenao.quickstart.operator.HeartbeatAlertFilter;
import io.github.sekelenao.quickstart.operator.HeartbeatAlertMapper;
import io.github.sekelenao.quickstart.pojo.HeartbeatAlert;
import io.github.sekelenao.quickstart.pojo.HeartbeatEvent;
import io.github.sekelenao.quickstart.serde.HeartbeatAlertSerializationSchema;
import io.github.sekelenao.quickstart.serde.HeartbeatDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatJob {

    private static final Logger log = LoggerFactory.getLogger(HeartbeatJob.class);

    public static void main(String[] args) throws Exception {
        // 1. Initialize Flinkboot with fail-fast YAML/CLI configuration
        var boot = Flinkboot.initialize(args);
        var config = boot.configuration(HeartbeatJobConfiguration.class);

        log.info("Starting job '{}' with max latency threshold: {}", config.job().name(), config.maxLatency());

        // 2. Obtain pre-configured StreamExecutionEnvironment
        var env = boot.executionEnvironment(config.job());

        // 3. Instantiate Kafka Source & Sink from Flinkboot properties
        var deserializer = KafkaRecordDeserializationSchema.valueOnly(new HeartbeatDeserializationSchema());
        var kafkaSource = KafkaSourceFactory.supplyFor(config.kafkaSource(), deserializer);

        var serializer = KafkaRecordSerializationSchema.<HeartbeatAlert>builder()
                .setTopic(config.kafkaSink().topic())
                .setValueSerializationSchema(new HeartbeatAlertSerializationSchema())
                .build();
        var kafkaSink = KafkaSinkFactory.supplyFor(config.kafkaSink(), serializer);

        // 4. Define streaming pipeline using dedicated operators
        var heartbeats = env.fromSource(
                kafkaSource,
                WatermarkStrategy.noWatermarks(),
                "Kafka Heartbeats Source"
        );

        var alerts = heartbeats
                .filter(new HeartbeatAlertFilter(config.maxLatency()))
                .name("Filter Unhealthy or High Latency")
                .map(new HeartbeatAlertMapper(config.maxLatency()))
                .name("Map to Alert");

        alerts.sinkTo(kafkaSink).name("Kafka Alerts Sink");

        // 5. Execute streaming job
        env.execute(config.job().name());
    }
}
