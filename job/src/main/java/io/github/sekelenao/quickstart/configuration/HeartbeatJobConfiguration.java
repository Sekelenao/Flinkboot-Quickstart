package io.github.sekelenao.quickstart.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.sekelenao.flinkboot.core.api.properties.JobProperties;
import io.github.sekelenao.flinkboot.kafka.api.properties.sink.KafkaSinkProperties;
import io.github.sekelenao.flinkboot.kafka.api.properties.source.KafkaSourceTopicListProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Duration;

public record HeartbeatJobConfiguration(
        @Valid @NotNull @JsonProperty("job") JobProperties job,
        @Valid @NotNull @JsonProperty("kafka-source") KafkaSourceTopicListProperties kafkaSource,
        @Valid @NotNull @JsonProperty("kafka-sink") KafkaSinkProperties kafkaSink,
        @NotNull @JsonProperty("max-latency") Duration maxLatency
) implements Serializable {
}
